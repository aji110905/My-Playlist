package aji.myplaylist.music;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Music {
    private final String TITLE;
    private final String ARTIST;
    private final String ALBUM;
    private final String YEAR;
    private final String COMMENT;
    private final byte[] COVER_IMAGE;
    private final String LYRICS;
    private final byte[] MUSIC_DATA;

    private static class Id3v2Result {
        String title;
        String artist;
        String album;
        String year;
        String comment;
        byte[] coverImage;
        String lyrics;
    }

    public Music(File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Invalid file: " + file.getAbsolutePath());
        }

        this.MUSIC_DATA = Files.readAllBytes(file.toPath());

        Id3v2Result id3v2Result = parseId3v2Tag();
        String[] id3v1Result = parseId3v1Tag();

        this.TITLE = id3v2Result.title != null ? id3v2Result.title : id3v1Result[0];
        this.ARTIST = id3v2Result.artist != null ? id3v2Result.artist : id3v1Result[1];
        this.ALBUM = id3v2Result.album != null ? id3v2Result.album : id3v1Result[2];
        this.YEAR = id3v2Result.year != null ? id3v2Result.year : id3v1Result[3];
        this.COMMENT = id3v2Result.comment != null ? id3v2Result.comment : id3v1Result[4];
        this.COVER_IMAGE = id3v2Result.coverImage;
        this.LYRICS = id3v2Result.lyrics;
    }

    private Id3v2Result parseId3v2Tag() {
        Id3v2Result result = new Id3v2Result();
        if (MUSIC_DATA.length < 10) {
            return result;
        }

        String header = new String(MUSIC_DATA, 0, 3, StandardCharsets.ISO_8859_1);
        if (!"ID3".equals(header)) {
            return result;
        }

        int majorVersion = MUSIC_DATA[3] & 0xFF;
        int revision = MUSIC_DATA[4] & 0xFF;
        int flags = MUSIC_DATA[5] & 0xFF;
        int tagSize = syncSafeToInt(MUSIC_DATA, 6, 4);
        int tagEndPos = 10 + tagSize;

        if (tagEndPos > MUSIC_DATA.length) {
            return result;
        }

        int frameIdLength = (majorVersion == 2) ? 3 : 4;
        int currentPos = 10;

        while (currentPos + frameIdLength + 4 + 2 <= tagEndPos) {
            String frameId = new String(MUSIC_DATA, currentPos, frameIdLength, StandardCharsets.ISO_8859_1);
            if (frameId.trim().isEmpty()) {
                break;
            }

            int frameSize;
            if (majorVersion == 2) {
                frameSize = bytesToInt(MUSIC_DATA, currentPos + frameIdLength, 3);
            } else {
                frameSize = (majorVersion == 4)
                        ? syncSafeToInt(MUSIC_DATA, currentPos + frameIdLength, 4)
                        : bytesToInt(MUSIC_DATA, currentPos + frameIdLength, 4);
            }

            int flagsPos = currentPos + frameIdLength + (majorVersion == 2 ? 3 : 4);
            int frameContentPos = flagsPos + 2;
            int frameEndPos = frameContentPos + frameSize;

            if (frameEndPos > tagEndPos) {
                break;
            }

            switch (frameId) {
                case "TIT":
                case "TIT2":
                    result.title = parseTextFrame(MUSIC_DATA, frameContentPos, frameSize);
                    break;
                case "TP1":
                case "TPE1":
                    result.artist = parseTextFrame(MUSIC_DATA, frameContentPos, frameSize);
                    break;
                case "TAL":
                case "TALB":
                    result.album = parseTextFrame(MUSIC_DATA, frameContentPos, frameSize);
                    break;
                case "TYE":
                case "TYER":
                case "TDRC":
                case "TDOR":
                    result.year = parseTextFrame(MUSIC_DATA, frameContentPos, frameSize);
                    break;
                case "COM":
                case "COMM":
                    result.comment = parseCommentFrame(MUSIC_DATA, frameContentPos, frameSize);
                    break;
                case "PIC":
                case "APIC":
                    result.coverImage = parseApicFrame(MUSIC_DATA, frameContentPos, frameSize);
                    break;
                case "ULT":
                case "USLT":
                    result.lyrics = parseUsltFrame(MUSIC_DATA, frameContentPos, frameSize);
                    break;
            }

            currentPos = frameEndPos;
        }

        return result;
    }

    private String[] parseId3v1Tag() {
        String[] result = new String[5];
        if (MUSIC_DATA.length < 128) {
            return result;
        }

        byte[] tagData = new byte[128];
        System.arraycopy(MUSIC_DATA, MUSIC_DATA.length - 128, tagData, 0, 128);

        if (!"TAG".equals(new String(tagData, 0, 3, StandardCharsets.ISO_8859_1))) {
            return result;
        }

        result[0] = trimNulls(new String(tagData, 3, 30, StandardCharsets.ISO_8859_1));
        result[1] = trimNulls(new String(tagData, 33, 30, StandardCharsets.ISO_8859_1));
        result[2] = trimNulls(new String(tagData, 63, 30, StandardCharsets.ISO_8859_1));
        result[3] = trimNulls(new String(tagData, 93, 4, StandardCharsets.ISO_8859_1));
        result[4] = trimNulls(new String(tagData, 97, 30, StandardCharsets.ISO_8859_1));
        return result;
    }

    private String trimNulls(String s) {
        if (s == null) return null;
        return s.replace("\0", "").trim();
    }

    private String parseTextFrame(byte[] data, int start, int length) {
        if (length < 1) return null;
        int encoding = data[start] & 0xFF;
        int contentStart = start + 1;
        int contentLength = length - 1;
        if (contentLength <= 0) return null;

        try {
            switch (encoding) {
                case 0: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.ISO_8859_1));
                case 1: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.UTF_16));
                case 2: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.UTF_16BE));
                case 3: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.UTF_8));
                default: return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String parseCommentFrame(byte[] data, int start, int length) {
        if (length < 5) return null;
        int encoding = data[start] & 0xFF;
        int descStart = start + 4;
        int descEnd = descStart;
        while (descEnd < start + length && data[descEnd] != 0) descEnd++;
        int contentStart = descEnd + 1;
        int contentLength = (start + length) - contentStart;
        if (contentLength <= 0) return null;

        try {
            switch (encoding) {
                case 0: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.ISO_8859_1));
                case 1: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.UTF_16));
                case 2: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.UTF_16BE));
                case 3: return trimNulls(new String(data, contentStart, contentLength, StandardCharsets.UTF_8));
                default: return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] parseApicFrame(byte[] data, int start, int length) {
        if (length < 3) return null;
        int mimeStart = start + 1;
        int mimeEnd = mimeStart;
        while (mimeEnd < start + length && data[mimeEnd] != 0) mimeEnd++;
        int descStart = mimeEnd + 2;
        int descEnd = descStart;
        while (descEnd < start + length && data[descEnd] != 0) descEnd++;
        int imageStart = descEnd + 1;
        int imageLength = (start + length) - imageStart;
        if (imageLength <= 0) return null;

        byte[] imageData = new byte[imageLength];
        System.arraycopy(data, imageStart, imageData, 0, imageLength);
        return imageData;
    }

    private String parseUsltFrame(byte[] data, int start, int length) {
        if (length < 5) return null;
        int encoding = data[start] & 0xFF;
        int descStart = start + 4;
        int descEnd = descStart;
        while (descEnd < start + length && data[descEnd] != 0) descEnd++;
        int lyricsStart = descEnd + 1;
        int lyricsLength = (start + length) - lyricsStart;
        if (lyricsLength <= 0) return null;

        try {
            switch (encoding) {
                case 0: return trimNulls(new String(data, lyricsStart, lyricsLength, StandardCharsets.ISO_8859_1));
                case 1: return trimNulls(new String(data, lyricsStart, lyricsLength, StandardCharsets.UTF_16));
                case 2: return trimNulls(new String(data, lyricsStart, lyricsLength, StandardCharsets.UTF_16BE));
                case 3: return trimNulls(new String(data, lyricsStart, lyricsLength, StandardCharsets.UTF_8));
                default: return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private int syncSafeToInt(byte[] data, int start, int length) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result = (result << 7) | (data[start + i] & 0x7F);
        }
        return result;
    }

    private int bytesToInt(byte[] data, int start, int length) {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result = (result << 8) | (data[start + i] & 0xFF);
        }
        return result;
    }

    // Getter方法
    public String getTitle() {
        return TITLE;
    }

    public String getArtist() {
        return ARTIST;
    }

    public String getAlbum() {
        return ALBUM;
    }

    public String getYear() {
        return YEAR;
    }

    public String getComment() {
        return COMMENT;
    }

    public byte[] getCoverImage() {
        return COVER_IMAGE != null ? COVER_IMAGE.clone() : null;
    }
    public String getLyrics() {
        return LYRICS;
    }
    public byte[] getMusicData() {
        return MUSIC_DATA.clone();
    }
}
