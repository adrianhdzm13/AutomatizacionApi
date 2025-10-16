package co.com.apirest.project.utils;

public enum Uri {
    ACCESS_TOKEN("/merchants/pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7"),
    TRANSACTIONS("/transactions");

    private String uri;

    Uri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
