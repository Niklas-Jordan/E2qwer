package sample;

public class WikiBooks {
    private String url;

    public WikiBooks(String url) {
        setUrl(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        url = url.replace("", "_");
        this.url = "https://de.wikibooks.org/wiki/Hauptseite" + url;
    }
}