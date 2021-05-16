package kr.co.manty;

public class Feed {
	
	private final String name;
	private final String url;

	public Feed(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

}
