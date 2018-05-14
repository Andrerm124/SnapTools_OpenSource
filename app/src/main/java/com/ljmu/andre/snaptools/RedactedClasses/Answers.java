package com.ljmu.andre.snaptools.RedactedClasses;

import timber.log.Timber;

/**
 * ===========================================================================
 * The class is a replacement to allow the broad range of code that relies
 * on the Fabric API to compile without an error.
 *
 * The code that managed the analytics in SnapTools was removed as it relied
 * upon private data (Such as API keys) to function, therefore it saw it best
 * to completely remove it before making it public.
 * ===========================================================================
 */
public class Answers {
	public static <T> T safeLogEvent(Object o) {
		Timber.e("FABRICS ANALYTICS WAS REDACTED FROM PUBLIC SOURCE!");
		return (T) new Object();
	}

	public static class CustomEvent {
		public CustomEvent(String msg) {
			Timber.e("FABRICS ANALYTICS WAS REDACTED FROM PUBLIC SOURCE!");
		}

		public CustomEvent putCustomAttribute(String s1, String s2) {
			Timber.e("FABRICS ANALYTICS WAS REDACTED FROM PUBLIC SOURCE!");

			return this;
		}
	}
}
