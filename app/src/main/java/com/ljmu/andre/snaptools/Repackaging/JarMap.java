package com.ljmu.andre.snaptools.Repackaging;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * ===========================================================================
 * STEALING MAGISK CODE YO
 * ===========================================================================
 */


public class JarMap implements Closeable, AutoCloseable {
	private JarFile jarFile;
	private JarInputStream jis;
	private boolean isInputStream = false;
	private LinkedHashMap<String, JarEntry> bufMap;

	public JarMap(String name) throws IOException {
		this(new File(name));
	}

	public JarMap(File file) throws IOException {
		this(file, true);
	}

	public JarMap(File file, boolean verify) throws IOException {
		this(file, verify, ZipFile.OPEN_READ);
	}

	public JarMap(File file, boolean verify, int mode) throws IOException {
		jarFile = new JarFile(file, verify, mode);
	}

	public JarMap(String name, boolean verify) throws IOException {
		this(new File(name), verify);
	}

	public JarMap(InputStream is) throws IOException {
		this(is, true);
	}

	public JarMap(InputStream is, boolean verify) throws IOException {
		isInputStream = true;
		bufMap = new LinkedHashMap<>();
		jis = new JarInputStream(is, verify);
		JarEntry entry;
		while ((entry = jis.getNextJarEntry()) != null) {
			bufMap.put(entry.getName(), new JarMapEntry(entry, jis));
		}
	}

	public File getFile() {
		return isInputStream ? null : new File(jarFile.getName());
	}

	public Manifest getManifest() throws IOException {
		return isInputStream ? jis.getManifest() : jarFile.getManifest();
	}

	public InputStream getInputStream(ZipEntry ze) throws IOException {
		return isInputStream ? ((JarMapEntry) bufMap.get(ze.getName())).data.getInputStream() :
				jarFile.getInputStream(ze);
	}

	public OutputStream getOutputStream(ZipEntry ze) {
		if (!isInputStream) // Only support InputStream mode
			return null;
		ByteArrayStream bs = ((JarMapEntry) bufMap.get(ze.getName())).data;
		bs.reset();
		return bs;
	}

	public byte[] getRawData(ZipEntry ze) throws IOException {
		if (isInputStream) {
			return ((JarMapEntry) bufMap.get(ze.getName())).data.toByteArray();
		} else {
			ByteArrayStream bytes = new ByteArrayStream();
			bytes.readFrom(jarFile.getInputStream(ze));
			return bytes.toByteArray();
		}
	}

	public Enumeration<JarEntry> entries() {
		return isInputStream ? Collections.enumeration(bufMap.values()) : jarFile.entries();
	}

	public ZipEntry getEntry(String name) {
		return getJarEntry(name);
	}

	public JarEntry getJarEntry(String name) {
		return isInputStream ? bufMap.get(name) : jarFile.getJarEntry(name);
	}

	@Override
	public void close() throws IOException {
		(isInputStream ? jis : jarFile).close();
	}

	private static class JarMapEntry extends JarEntry {
		ByteArrayStream data;

		JarMapEntry(JarEntry je, InputStream is) {
			super(je);
			data = new ByteArrayStream();
			data.readFrom(is);
		}
	}
}