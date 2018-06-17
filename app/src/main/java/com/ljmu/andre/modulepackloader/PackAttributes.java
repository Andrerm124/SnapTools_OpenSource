package com.ljmu.andre.modulepackloader;

import java.util.jar.Attributes;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */
public interface PackAttributes {

	<T extends PackAttributes> T onBuild(Attributes attributes);

	class TestPackAttributes implements PackAttributes {
		private String type;
		private String packVersion;
		private String scVersion;

		@Override public <T extends PackAttributes> T onBuild(Attributes attributes) {
			this.type = attributes.getValue("Type");
			this.packVersion = attributes.getValue("PackVersion");
			this.scVersion = attributes.getValue("SCVersion");

			return (T) this;
		}

		@Override public String toString() {
			return "TestPackAttributes{" +
					"type='" + type + '\'' +
					", packVersion='" + packVersion + '\'' +
					", scVersion='" + scVersion + '\'' +
					'}';
		}
	}
}
