package com.systemweb.webmapsystem.model;

import java.util.List;

public class PdfEditRequest {
    private String file;
    private List<Edit> edits;

    

    public String getFile() {
        return file;
    }



    public void setFile(String file) {
        this.file = file;
    }



    public List<Edit> getEdits() {
        return edits;
    }



    public void setEdits(List<Edit> edits) {
        this.edits = edits;
    }



    public static class Edit {
        private String x;
        private String y;
        private String text;
        
        public String getX() {
            return x;
        }
        public void setX(String x) {
            this.x = x;
        }
        public String getY() {
            return y;
        }
        public void setY(String y) {
            this.y = y;
        }
        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }

        
    }
}
