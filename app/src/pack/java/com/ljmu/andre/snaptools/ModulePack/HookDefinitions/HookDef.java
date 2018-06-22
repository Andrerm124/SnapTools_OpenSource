package com.ljmu.andre.snaptools.ModulePack.HookDefinitions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ljmu.andre.ConstantDefiner.Constant;
import com.ljmu.andre.ConstantDefiner.ConstantDefiner;
import com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.HookClass;
import com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookDef.Hook;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CAMERA_FRAGMENT;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CAPTION_MANAGER_CLASS;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_IMAGE_METADATA;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_MESSAGE_METADATA;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_MESSAGE_VIEW_HOLDER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_METADATA;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_METADATA_JSON_PARSER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_NOTIFICATION_CREATOR;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_V3_FRAGMENT;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_VIDEO;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CHAT_VIDEO_METADATA;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.CONCENTRIC_TIMERVIEW;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.COUNTDOWNTIMER_VIEW;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.ENCRYPTED_STREAM_BUILDER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.ENCRYPTION_ALGORITHM;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.FILTER_METADATA;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.FILTER_METADATA_CREATOR;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.FILTER_METADATA_LOADER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.FONT_CLASS;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.FRIEND_PROFILE_POPUP_FRAGMENT;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.GALLERY_SNAP;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.GEOFILTER_VIEW;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.GEOFILTER_VIEW_CREATOR;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.GROUP_SNAP_METADATA;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.LENS;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.LENS_ASSET_SERIALISER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.LENS_AUTHENTICATION;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.LENS_CATEGORY_RESOLVER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.LENS_LOADER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.META_DATA_BUILDER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.NETWORK_DISPATCHER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.NETWORK_MANAGER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.NEW_CONCENTRIC_TIMERVIEW;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.OPERA_PAGE_VIEW;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.PROFILE_SETTINGS_CREATOR;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.RECEIVED_SNAP;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.RECEIVED_SNAP_ENCRYPTION;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.RECEIVED_SNAP_PAYLOAD_BUILDER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SCREENSHOT_DETECTOR;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SENT_BATCHED_VIDEO;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SENT_SNAP_BASE;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SENT_VIDEO;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SNAP_BASE;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SNAP_COUNTDOWN_CONTROLLER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SNAP_DATA_JSON;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SNAP_STATUS;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.SNAP_TIMER_VIEW;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_ADVANCER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_FRIEND_VIEWED;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_LOADER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_METADATA;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_METADATA_LOADER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_SNAP;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_SNAP_AD_LOADER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_SNAP_PAYLOAD_BUILDER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.STORY_STATUS_UPDATER;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.TEXTURE_VIDEO_VIEW;
import static com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.USER_PREFS;
import static com.ljmu.andre.snaptools.Utils.StringEncryptor.decryptMsg;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */
public class HookDef extends ConstantDefiner<Hook> {
	public static final HookDef INST = new HookDef();

	// SAVING ====================================================================
	public static final Hook ENCRYPTION_ALGORITHM_STREAM = new Hook(
			/*ENCRYPTION_ALGORITHM_STREAM*/ decryptMsg(new byte[]{95, -64, 95, 21, 52, -52, 92, -16, -33, -81, 77, -44, 74, 81, 54, -76, 66, 58, -75, 11, 101, 0, 50, 101, -124, 112, 6, -35, 46, 95, -85, -57}),
			ENCRYPTION_ALGORITHM, "b", InputStream.class
	);
	public static final Hook STREAM_TYPE_CHECK_BYPASS = new Hook(
			/*STREAM_TYPE_CHECK_BYPASS*/ decryptMsg(new byte[]{-122, 113, 114, -81, 123, -50, -102, -44, -125, 11, 83, 65, -121, -126, -30, 47, -97, 34, 114, -15, -113, 108, -101, -103, 32, -35, 111, 4, -42, 12, 66, -30}),
			ENCRYPTED_STREAM_BUILDER, "a", "tu", int.class, int.class
	);
	public static final Hook STORY_GET_ALGORITHM = new Hook(
			/*STORY_GET_ALGORITHM*/ decryptMsg(new byte[]{-36, -58, -38, -50, 58, -105, 118, 54, -7, 83, -2, 20, -83, 63, 32, 7, -94, 22, -98, -70, -51, 13, 24, 119, -64, 41, -46, -118, -73, -67, 110, -19}),
			STORY_SNAP, "aK"
	);
	public static final Hook DIRECT_GET_ALGORITHM = new Hook(
			/*DIRECT_GET_ALGORITHM*/ decryptMsg(new byte[]{4, 117, -7, -117, -68, 52, 74, -44, -80, 20, 122, -24, 106, 8, 57, 110, 26, 47, -104, 46, -32, -96, -36, -62, 71, -123, -62, -24, 36, -90, 110, -78}),
			RECEIVED_SNAP_ENCRYPTION, "a", "mlv", String.class
	);
	public static final Hook CHAT_IMAGE_GET_ALGORITHM = new Hook(
			/*CHAT_IMAGE_GET_ALGORITHM*/ decryptMsg(new byte[]{-51, 61, 98, -114, -79, -4, 112, -68, 94, 20, -112, 17, -92, -55, 67, 28, -80, 104, -86, -10, -93, -5, 14, 42, 65, -14, 22, -30, 11, -20, -80, 92}),
			CHAT_IMAGE_METADATA, "c", "iwy"
	);
	public static final Hook CHAT_VIDEO_GET_ALGORITHM = new Hook(
			/*CHAT_VIDEO_GET_ALGORITHM*/ decryptMsg(new byte[]{-113, -80, -94, 18, -33, 49, 83, -99, -102, -98, 23, 38, -17, 108, -65, -118, -80, 104, -86, -10, -93, -5, 14, 42, 65, -14, 22, -30, 11, -20, -80, 92}),
			CHAT_VIDEO_METADATA, "e"
	);
	public static final Hook CHAT_VIDEO_PATH = new Hook(
			/*CHAT_VIDEO_PATH*/ decryptMsg(new byte[]{-45, 10, -103, 102, -60, -126, 62, -86, 68, -81, -126, 103, 2, 17, 115, 26}),
			CHAT_VIDEO, "dA_"
	);
	public static final Hook GROUP_GET_ALGORITHM = new Hook(
			/*GROUP_GET_ALGORITHM*/ decryptMsg(new byte[]{22, -2, -127, -55, 99, -16, -92, 116, 70, 109, 24, 53, -65, 95, -24, 69, -94, 22, -98, -70, -51, 13, 24, 119, -64, 41, -46, -118, -73, -67, 110, -19}),
			GROUP_SNAP_METADATA, "c", "jad"
	);
	public static final Hook SENT_SNAP = new Hook(
			/*SENT_SNAP*/ decryptMsg(new byte[]{82, -94, 126, -119, -89, 32, 87, -37, 6, 117, -25, -78, -43, 80, 14, 68}),
			META_DATA_BUILDER, "a", SENT_SNAP_BASE.getStrClass()
	);
	public static final Hook SENT_BATCHED_SNAP = new Hook(
			/*SENT_BATCHED_SNAP*/ decryptMsg(new byte[]{124, -51, -112, 99, 79, 115, -70, -126, 70, 81, -120, 91, -28, 99, -91, 93, 42, -92, 127, 10, 40, 65, -70, -67, -40, -37, 84, -3, 18, 89, -30, -123}),
			SENT_BATCHED_VIDEO, "a", "mlj$b"
	);
	public static final Hook CONSTRUCTOR_OPERA_PAGE_VIEW = new Hook(
			/*CONSTRUCTOR_OPERA_PAGE_VIEW*/ decryptMsg(new byte[]{81, -12, 69, 103, -76, -121, 103, -83, 87, 37, 111, 97, -89, 63, -116, -85, -57, -124, -68, -58, 70, 84, 44, 42, -24, -75, -4, 106, -18, 3, 50, -117}),
			OPERA_PAGE_VIEW, null, Context.class
	);
	// ===========================================================================

	// UNLIMITED VIEWING =========================================================
	public static final Hook GALLERY_SNAP_GET_DURATION = new Hook(
			/*GALLERY_SNAP_GET_DURATION*/ decryptMsg(new byte[]{89, -46, -47, -72, -35, -56, -21, 39, -21, -76, -47, -89, 39, -30, 78, 114, 62, -97, -41, 92, -40, -43, -24, -118, 74, -10, -95, 84, -39, 117, -80, 0}),
			GALLERY_SNAP, "w"
	);
	public static final Hook STORY_METADATA_INSERT_OBJECT = new Hook(
			/*STORY_METADATA_INSERT_OBJECT*/ decryptMsg(new byte[]{-79, -47, -73, 80, 58, 123, 12, -126, 45, 10, 66, -98, 6, 127, 67, 98, -53, -7, 72, -125, 36, 14, 61, 23, 9, -124, -69, 5, 91, -87, 93, 40}),
			STORY_METADATA, "b", String.class, Object.class
	);
	public static final Hook SNAP_COUNTDOWN_POSTER = new Hook(
			/*SNAP_COUNTDOWN_POSTER*/ decryptMsg(new byte[]{120, 112, 37, -102, -76, -24, 25, 14, -99, -114, 28, -57, -41, -76, 101, 11, -97, -122, 29, 50, -122, -100, 63, -79, 97, 38, -88, 88, 22, -3, 90, 50}),
			SNAP_COUNTDOWN_CONTROLLER, "a", long.class
	);
	public static final Hook TEXTURE_VIDVIEW_START = new Hook(
			/*TEXTURE_VIDVIEW_START*/ decryptMsg(new byte[]{-30, -41, -47, 56, -1, 121, -73, 83, 7, 40, -1, 8, 120, 124, -16, 62, 96, -120, 106, -5, -126, -58, 13, -67, 87, -125, -23, 9, 73, -81, 68, -58}),
			TEXTURE_VIDEO_VIEW, "start"
	);
	public static final Hook TEXTURE_VIDVIEW_SETLOOPING = new Hook(
			/*TEXTURE_VIDVIEW_SETLOOPING*/ decryptMsg(new byte[]{-30, -41, -47, 56, -1, 121, -73, 83, 7, 40, -1, 8, 120, 124, -16, 62, 63, -37, 84, 56, -57, 4, -95, -80, 23, 37, -54, -68, -35, -83, 29, 113}),
			TEXTURE_VIDEO_VIEW, "setLooping", boolean.class
	);
	// ===========================================================================

	// SHARING ===================================================================
	/*public static final Hook BUILD_SHARED_MEDIA_META_DATA = new Hook(
			/*BUILD_SHARED_MEDIA_META_DATA decryptMsg(new byte[]{90, 6, 62, -39, -45, -96, 24, -99, -128, -116, -36, 112, 124, -26, -107, 103, 94, 62, -33, 88, -15, 69, 13, -117, 83, -46, 43, -118, 15, -47, 41, 95}),
			SHARED_MEDIA_BUILDER, "a"
	);*/
	public static final Hook REPLACE_SHARED_IMAGE = new Hook(
			/*REPLACE_SHARED_IMAGE*/ decryptMsg(new byte[]{-61, -32, -116, -14, -10, 10, -109, -84, 117, 65, -67, -101, -23, -20, 38, 21, 53, 5, 6, -44, 87, -80, 69, -65, -115, -35, -18, -49, 119, 55, -69, 20}),
			CAMERA_FRAGMENT, "a", Bitmap.class, String.class, long.class, boolean.class, int.class, "nmy$b"
	);
	public static final Hook REPLACE_SHARED_VIDEO = new Hook(
			/*REPLACE_SHARED_VIDEO*/ decryptMsg(new byte[]{-51, -74, -3, 28, 86, -36, 13, -86, 5, -64, 97, 28, 104, 16, -59, 16, 49, -33, 95, 14, -69, -70, -96, 74, 107, -66, 80, 15, 124, 35, 90, 8}),
			CAMERA_FRAGMENT, "a", Uri.class, int.class, boolean.class, "ovx", long.class
	);
	public static final Hook BATCHED_MEDIA_LIMITER = new Hook(
			/*BATCHED_MEDIA_LIMITER*/ decryptMsg(new byte[]{-44, -14, -59, 117, 55, -9, 126, -37, 115, -103, -14, -125, -26, -19, 122, -22, 1, -100, 4, 78, -72, 96, 50, 109, -88, 69, -92, 46, 99, 6, 46, -36}),
			SENT_VIDEO, "af"
	);
	public static final Hook CAMERA_IS_VISIBLE = new Hook(
			/*CAMERA_IS_VISIBLE*/ decryptMsg(new byte[]{-36, -102, 8, 16, -61, -10, -115, 75, -21, 29, -36, 4, -101, -87, 11, -89, -37, -50, -10, 27, -98, -89, -128, 49, 58, -45, 112, -48, -76, 15, 50, -13}),
			CAMERA_FRAGMENT, "onVisible"
	);
	// ===========================================================================

	// LENSES ====================================================================
	public static final Hook LENS_LOADING = new Hook(
			/*LENS_LOADING*/ decryptMsg(new byte[]{99, 99, 4, 22, -92, -122, -73, 12, -17, -57, 83, -20, -59, -51, -102, -42}),
			LENS_LOADER, "a", List.class
	);
	public static final Hook CHECK_LENS_AUTH = new Hook(
			/*CHECK_LENS_AUTH*/ decryptMsg(new byte[]{52, 125, 23, 74, -95, 122, 110, 65, -67, 19, 126, -100, 73, -102, -63, -100}),
			LENS_AUTHENTICATION, "a", LENS.getStrClass(), String.class
	);
	public static final Hook CHECK_LENS_CATEGORY_AUTH = new Hook(
			/*CHECK_LENS_CATEGORY_AUTH*/ decryptMsg(new byte[]{-27, 111, 91, 70, -109, -93, 34, -52, -68, 105, 9, 99, 107, 105, 4, 51, -68, -94, -110, 37, -125, -12, 29, -15, -44, 103, 32, 11, -2, -98, -104, -120}),
			LENS_AUTHENTICATION, "a", "hly", String.class
	);
	public static final Hook CHECK_LENS_ASSET_AUTH = new Hook(
			/*CHECK_LENS_ASSET_AUTH*/ decryptMsg(new byte[]{21, 79, 92, 19, 97, 51, -61, 117, -118, 114, -17, -53, 86, 78, -110, 62, 82, -61, 71, 86, -75, 109, -24, 46, 14, 90, -104, 50, 86, 118, -5, -95}),
			LENS_AUTHENTICATION, "a", "hlx", String.class
	);
	/*public static final Hook CHECK_GEO_RANGE = new Hook(
			/*CHECK_GEO_RANGE decryptMsg(new byte[]{-119, 48, 117, 112, 91, -82, 60, 9, 115, -2, -8, 46, 48, 1, -122, 118}),
			LENS, "a", Location.class
	);*/
	public static final Hook RESOLVE_LENS_CATEGORY = new Hook(
			/*RESOLVE_LENS_CATEGORY*/ decryptMsg(new byte[]{-122, 94, 5, 60, 117, -49, 122, 22, 64, 64, -34, 79, 5, -55, -24, 83, -34, 120, -87, 97, -120, 72, -93, 59, 82, 14, -113, 14, -120, -26, -13, -70}),
			LENS_CATEGORY_RESOLVER, "a", String.class
	);
	public static final Hook LENS_ASSET_READING = new Hook(
			/*LENS_ASSET_READING*/ decryptMsg(new byte[]{18, 127, 24, 65, -42, -12, -24, 69, 122, -1, 62, -35, 26, -2, -92, 123, 59, 63, -23, 120, -10, -56, -15, -35, 52, 99, -105, 97, 125, 68, 76, 23}),
			LENS_ASSET_SERIALISER, "a", "com.google.gson.stream.JsonReader"
	);

	// ===========================================================================

	// AD BLOCKING ===============================================================
	public static final Hook LOAD_STORIES = new Hook(
			/*LOAD_STORIES*/ decryptMsg(new byte[]{27, 36, -42, 24, -36, -118, -94, -4, 112, -1, -48, 95, 34, -99, 9, -118}),
			STORY_LOADER, "a", List.class
	);
	public static final Hook LOAD_STORY_SNAP_ADVERT = new Hook(
			/*LOAD_STORY_SNAP_ADVERT*/ decryptMsg(new byte[]{-126, -54, 6, -33, -51, 54, -73, 68, -78, -83, -77, 47, 22, 93, 18, 30, -40, 11, -11, -127, -5, 84, -106, -97, -106, -73, -20, 36, -79, -32, -86, -109}),
			STORY_SNAP_AD_LOADER, "a", "czh", "qsm"
	);
	public static final Hook FRIEND_STORY_TILE_USERNAME = new Hook(
			/*FRIEND_STORY_TILE_USERNAME*/ decryptMsg(new byte[]{124, 66, -85, 21, -61, -99, -32, -2, 42, -81, -110, 115, 89, 42, -114, 110, 110, 3, 28, 121, -121, -79, 25, -19, -108, 64, 112, 59, 122, -64, -40, 7}),
			STORY_FRIEND_VIEWED, "r_"
	);
	public static final Hook FRIEND_PROFILE_POPUP_CREATED = new Hook(
			/*FRIEND_PROFILE_POPUP_CREATED*/ decryptMsg(new byte[]{59, -52, -36, -28, 33, 44, -117, -10, 73, -65, -47, -69, 17, -5, 116, 74, 32, -4, 57, -113, 126, -88, 36, 44, 98, 23, -7, -61, -79, 31, 121, 7}),
			FRIEND_PROFILE_POPUP_FRAGMENT, "onViewCreated", View.class, Bundle.class
	);
	// ===========================================================================

	// CHAT MANAGER ===============================================================
	public static final Hook CHAT_METADATA_READ = new Hook(
			/*CHAT_METADATA_READ*/ decryptMsg(new byte[]{-79, -123, 33, 109, 80, 66, -109, -50, -37, 11, -38, 72, -63, 32, 61, -79, -67, 63, 25, -12, 116, -69, 45, -101, -74, -85, 37, 42, -113, 7, 59, 90}),
			CHAT_METADATA_JSON_PARSER, "a", /*com.google.gson.stream.JsonReader*/ decryptMsg(new byte[]{18, -79, -128, 109, 34, -54, 64, -101, -29, -92, 91, -79, 78, -101, 92, 81, -33, -84, 74, 38, -59, -117, -115, 127, -122, 53, -68, 49, 36, 93, -77, 50, 61, -115, 40, -37, -126, 96, -115, -114, -18, -95, -4, 94, 3, 61, 102, -45})
	);
	public static final Hook CHAT_METADATA_WRITE = new Hook(
			/*CHAT_METADATA_WRITE*/ decryptMsg(new byte[]{95, -118, -50, -4, -122, -44, -8, 52, -8, -65, -81, 71, 56, -25, -21, 67, -76, 25, -50, 77, -89, 17, -64, 32, 54, 87, 17, 79, -12, 81, 18, -42}),
			CHAT_METADATA_JSON_PARSER, "a", /*com.google.gson.stream.JsonWriter*/ decryptMsg(new byte[]{18, -79, -128, 109, 34, -54, 64, -101, -29, -92, 91, -79, 78, -101, 92, 81, 32, 52, 32, 79, 82, 30, -88, -12, 116, 79, 57, 66, -14, 29, -11, 88, 61, -115, 40, -37, -126, 96, -115, -114, -18, -95, -4, 94, 3, 61, 102, -45}),
			"qxz"
	);
	public static final Hook CHAT_MESSAGE_VIEW_MEASURE = new Hook(
			/*CHAT_MESSAGE_VIEW_MEASURE*/ decryptMsg(new byte[]{-12, 90, 80, -45, 77, -126, -20, -83, -116, -112, 116, 116, -78, -98, 60, 126, 62, 108, -85, -116, 79, -18, 89, 17, 16, 16, 35, -31, -49, -3, 76, 118}),
			CHAT_MESSAGE_VIEW_HOLDER, "P"
	);
	public static final Hook CHAT_ISSAVED_INAPP = new Hook(
			/*CHAT_ISSAVED_INAPP*/ decryptMsg(new byte[]{47, 1, 96, -80, -24, 53, 121, 23, 7, 26, 64, -73, -18, 11, -73, -68, 91, 48, 78, -33, 123, 21, 3, 16, 66, -43, -31, 33, -96, -66, 58, -94}),
			null, "t"
			/** FOUND IN -> abstract class iwh*/
	);
	public static final Hook CHAT_SAVE_INAPP = new Hook(
			/*CHAT_SAVE_INAPP*/ decryptMsg(new byte[]{38, -56, 63, -5, -109, 48, -13, -27, -44, -87, 126, -56, 107, -72, 61, 40}),
			CHAT_MESSAGE_VIEW_HOLDER, "M"
	);
	public static final Hook CHAT_NOTIFICATION = new Hook(
			/*CHAT_NOTIFICATION*/ decryptMsg(new byte[]{-55, 31, -60, 93, 69, -20, 14, -13, -110, -101, 29, 30, 21, -87, 39, 72, -77, 58, -105, 113, 47, -16, 87, 19, 84, -114, -13, -127, 30, 53, -68, -56}),
			CHAT_NOTIFICATION_CREATOR, "a", "nvt", "nvm"
	);

	// SCREENSHOT BYPASS =========================================================
	public static final Hook SCREENSHOT_BYPASS = new Hook(
			/*SCREENSHOT_BYPASS*/ decryptMsg(new byte[]{37, -99, 90, 107, 113, -104, 125, 48, -67, 126, -37, 74, 87, -103, 73, 19, -85, -101, 17, 37, 13, 50, 124, 103, -118, 99, 23, 44, -35, -78, -58, -3}),
			SCREENSHOT_DETECTOR, "a", LinkedHashMap.class
	);
	public static final Hook SET_SCREENSHOT_COUNT = new Hook(
			/*SET_SCREENSHOT_COUNT*/ decryptMsg(new byte[]{-72, 57, 67, 112, -126, 37, -17, 89, -116, -100, -92, -3, -9, -45, 14, -70, -56, -55, 116, -20, 73, -9, 36, 77, 47, -85, -58, 83, 84, -14, 4, -68}),
			SNAP_DATA_JSON, "a", Long.class
	);
	public static final Hook SET_VIDEO_SCREENSHOT_COUNT = new Hook(
			/*SET_VIDEO_SCREENSHOT_COUNT*/ decryptMsg(new byte[]{110, -68, -53, 62, 106, -55, 17, 22, -65, 53, -98, -122, -1, 117, 102, 7, 84, -27, 83, -117, -72, 23, 68, 23, 68, -73, -8, -74, -5, 87, -69, -10}),
			SNAP_DATA_JSON, "c", Long.class
	);
	public static final Hook SET_SCREENSHOT_COUNT3 = new Hook(
			/*SET_SCREENSHOT_COUNT*/ decryptMsg(new byte[]{-72, 57, 67, 112, -126, 37, -17, 89, -116, -100, -92, -3, -9, -45, 14, -70, -56, -55, 116, -20, 73, -9, 36, 77, 47, -85, -58, 83, 84, -14, 4, -68}),
			SNAP_DATA_JSON, "d", Long.class
	);
	// ===========================================================================

	// CUSTOM FILTERS ============================================================
	public static final Hook GEOFILTER_SHOULD_SUBSAMPLE = new Hook(
			/*GEOFILTER_SHOULD_SUBSAMPLE*/ decryptMsg(new byte[]{-58, 36, 123, -10, 24, -104, 97, -97, -61, -96, 74, 109, -39, 120, 105, -101, 80, 52, -4, 52, 99, 76, -30, -2, -85, -71, -92, 53, -58, -42, -97, -110}),
			GEOFILTER_VIEW, "a", GEOFILTER_VIEW.getStrClass(), boolean.class
	);
	public static final Hook FILTER_LOAD_METADATA = new Hook(
			/*FILTER_LOAD_METADATA*/ decryptMsg(new byte[]{-70, -34, -117, 47, -116, -42, 23, -86, 28, 63, 61, 40, -37, 48, 4, 18, 92, 65, 125, 123, -4, -42, -49, -8, 73, 110, 1, -77, 18, -55, 111, 95}),
			FILTER_METADATA_LOADER, "a", List.class, Context.class
	);
	public static final Hook CREATE_FILTER_METADATA = new Hook(
			/*CREATE_FILTER_METADATA*/ decryptMsg(new byte[]{-75, 116, -126, -108, 36, -1, 56, -93, -62, -31, -91, 91, -19, -100, -105, 34, 93, 127, -89, -18, -65, 15, 98, 28, -97, -116, 120, -41, 1, 48, 122, -89}),
			FILTER_METADATA_CREATOR, "e"
	);
	public static final Hook GET_GEOFILTER_CONTENT_VIEW = new Hook(
			/*GET_GEOFILTER_CONTENT_VIEW*/ decryptMsg(new byte[]{82, -121, -37, 15, -90, 52, 101, 69, -98, 124, -110, -57, 41, 86, -96, -5, -59, -33, -99, 109, 44, 119, 10, 14, 107, -74, 96, -13, 21, -112, -61, -52}),
			GEOFILTER_VIEW, "c"
	);
	public static final Hook CREATE_GEOFILTER_VIEW = new Hook(
			/*CREATE_GEOFILTER_VIEW*/ decryptMsg(new byte[]{80, -90, 24, 17, -3, 86, -33, -2, 112, 14, 67, 117, 93, 123, -117, -63, -107, 21, -112, 125, 71, 49, 32, -45, 80, 60, -33, 102, 5, -77, -110, -2}),
			GEOFILTER_VIEW_CREATOR, "a", FILTER_METADATA.getStrClass(), Context.class, "bao"
	);
	public static final Hook GEOFILTER_TAPPED = new Hook(
			/*GEOFILTER_TAPPED*/ decryptMsg(new byte[]{-14, 96, -41, -25, -9, 70, 37, 54, -13, 49, 77, -53, -83, 24, 0, 45, 104, -50, 116, -85, 125, 59, 23, -57, 29, -117, 35, 62, -41, 17, -14, 66}),
			GEOFILTER_VIEW, "a", MotionEvent.class
	);
	// ===========================================================================

	// MISC HOOKS ================================================================
	public static final Hook FONT_HOOK = new Hook(
			/*FONT_HOOK*/ decryptMsg(new byte[]{12, -102, -91, 60, -36, -54, -93, -106, 97, 34, 63, -13, 18, -36, 7, 88}),
			FONT_CLASS, "createFromFile", String.class
	);
	public static final Hook CAPTION_CREATE_HOOK = new Hook(
			/*CAPTION_CREATE_HOOK*/ decryptMsg(new byte[]{34, 30, 50, -119, 68, 115, -113, -117, -12, 33, 30, -10, 109, -80, -91, -33, -123, -32, 61, -47, -39, -66, -90, 75, -51, -101, 115, 124, -27, -95, -91, 88}),
			CAPTION_MANAGER_CLASS, "onCreateActionMode", ActionMode.class, Menu.class
	);

	// ===========================================================================

	// STEALTH VIEWING ===========================================================
	public static final Hook GET_SNAP_ID = new Hook(
			/*GET_SNAP_ID*/ decryptMsg(new byte[]{118, 87, 13, 104, -24, -61, 86, -39, -57, 91, -127, 126, 91, 53, -109, 126}),
			SNAP_BASE, "a"
	);
	public static final Hook SET_SNAP_STATUS = new Hook(
			/*SET_SNAP_STATUS*/ decryptMsg(new byte[]{-84, -64, 86, 20, 72, -120, -98, -20, -14, -4, 56, 33, 16, 62, 59, -20}),
			SNAP_BASE, "a", SNAP_STATUS.getStrClass()
	);
	public static final Hook MARK_STORY_VIEWED = new Hook(
			/*MARK_STORY_VIEWED*/ decryptMsg(new byte[]{-33, -62, 25, 7, -27, -96, -121, -35, -34, -99, -106, -43, 73, 126, 76, -26, -61, 51, 76, 58, 125, 17, 91, -2, 108, -2, 64, -26, -34, 116, -114, -21}),
			STORY_STATUS_UPDATER, "a", STORY_SNAP.getStrClass(), boolean.class
	);
	public static final Hook GET_RECEIVED_SNAP_PAYLOAD = new Hook(
			/*GET_RECEIVED_SNAP_PAYLOAD*/ decryptMsg(new byte[]{2, 78, -125, -9, -8, 120, 32, -25, -41, 100, -3, 36, -92, -108, -44, 16, -94, -16, 126, 89, -63, 97, 92, 109, -36, 31, -93, 97, -73, -61, 107, 8}),
			RECEIVED_SNAP_PAYLOAD_BUILDER, /*getRequestPayload*/ decryptMsg(new byte[]{-22, 72, 62, 2, 8, 18, 38, 119, 35, -53, 116, -34, -6, 28, 72, 81, -105, 124, 6, -98, 51, -78, -54, -47, -29, -81, 28, 106, -101, 96, -35, 32})
	);
	public static final Hook GET_STORY_SNAP_PAYLOAD = new Hook(
			/*GET_STORY_SNAP_PAYLOAD*/ decryptMsg(new byte[]{-47, 127, 18, 2, 2, -37, 65, 77, 15, 59, -122, 126, 29, 88, -65, -74, 28, 108, -82, 93, -30, 47, -72, 46, -59, -47, 103, -44, -100, -85, 80, -127}),
			STORY_SNAP_PAYLOAD_BUILDER, /*getRequestPayload*/ decryptMsg(new byte[]{-22, 72, 62, 2, 8, 18, 38, 119, 35, -53, 116, -34, -6, 28, 72, 81, -105, 124, 6, -98, 51, -78, -54, -47, -29, -81, 28, 106, -101, 96, -35, 32})
	);
	public static final Hook NETWORK_EXECUTE_SYNC = new Hook(
			/*NETWORK_EXECUTE_SYNC*/ decryptMsg(new byte[]{101, -48, 21, -102, -36, -41, -119, -89, -118, 58, -96, -126, -26, 28, -35, 84, -77, 49, -120, -37, -1, 42, 106, 120, -72, 98, -96, 74, 18, 26, -98, 36}),
			NETWORK_MANAGER, /*executeSynchronously*/ decryptMsg(new byte[]{78, -78, 31, 62, -52, 18, 87, 22, 64, 106, 15, 47, 37, 20, -44, -17, 44, -97, -30, -20, -47, 4, 0, 41, -61, 23, 94, -59, -119, 91, 21, -69})
	);
	public static final Hook DISPATCH_CHAT_UPDATE = new Hook(
			/*DISPATCH_CHAT_UPDATE*/ decryptMsg(new byte[]{105, 120, 37, -79, -9, 23, -68, -61, -24, 97, 114, 78, 3, 32, 58, -29, -20, 95, -44, -107, 62, 14, 59, -99, -128, 43, -6, -81, -89, 65, -58, 20}),
			NETWORK_DISPATCHER, "a", "iyh", "raf"
	);
	public static final Hook MARK_CHAT_VIEWED = new Hook(
			/*MARK_CHAT_VIEWED*/ decryptMsg(new byte[]{-101, 79, 111, -51, -99, 5, 77, 0, 109, 109, -124, -97, -79, 100, -83, 91, 104, -50, 116, -85, 125, 59, 23, -57, 29, -117, 35, 62, -41, 17, -14, 66}),
			CHAT_MESSAGE_METADATA, "b", long.class
	);
	public static final Hook CHAT_V3_FRAGMENT_CREATED = new Hook(
			/*CHAT_V3_FRAGMENT_CREATED*/ decryptMsg(new byte[]{60, 22, -104, 60, 102, 6, 52, -7, -61, 1, 3, -76, 31, 11, 51, -119, 106, -55, -12, 110, 111, 22, -106, -116, 95, 83, 5, 77, -114, 21, -99, -30}),
			CHAT_V3_FRAGMENT, "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class
	);
	public static final Hook CREATE_PROFILE_SETTINGS_VIEW = new Hook(
			/*CREATE_PROFILE_SETTINGS_VIEW*/ decryptMsg(new byte[]{-70, -126, 122, -44, -67, -121, -36, 19, 45, -114, -61, 101, 111, -79, -127, -107, 125, -49, -119, 20, 16, 23, -121, -9, 24, -75, -34, 73, -110, 121, 3, -70}),
			PROFILE_SETTINGS_CREATOR, "a", ViewGroup.class, int.class
	);
	// ===========================================================================

	public static final Hook SNAPTIMERVIEW_ONDRAW = new Hook(
			/*SNAPTIMERVIEW_ONDRAW*/ decryptMsg(new byte[]{-103, 103, 22, -94, 50, -2, 48, -109, -96, -26, 30, 97, -119, -84, -30, -94, -73, -71, -12, -81, -33, 45, 81, 114, 8, -48, -51, -110, -9, 25, -117, 29}),
			SNAP_TIMER_VIEW, "onDraw", Canvas.class
	);
	public static final Hook SNAP_GET_USERNAME = new Hook(
			/*SNAP_GET_USERNAME*/ decryptMsg(new byte[]{111, 115, 89, -82, 47, 49, -60, 21, 40, -102, -28, -82, 34, -118, -10, -11, -37, -50, -10, 27, -98, -89, -128, 49, 58, -45, 112, -48, -76, 15, 50, -13}),
			STORY_SNAP, "h"
	);
	public static final Hook SNAP_GET_TIMESTAMP = new Hook(
			/*SNAP_GET_TIMESTAMP*/ decryptMsg(new byte[]{31, -35, -85, 127, 117, 76, 103, 21, 120, 126, -55, -10, -14, 114, -83, -89, -92, 90, 23, 99, 119, 16, -50, 13, 11, 99, 94, -42, -15, 62, -5, -75}),
			STORY_SNAP, "n"
	);
	public static final Hook SNAP_GET_MEDIA_TYPE = new Hook(
			/*SNAP_GET_MEDIA_TYPE*/ decryptMsg(new byte[]{66, -24, 20, -26, 55, -80, 48, -48, 113, 62, -90, 31, -124, 79, -72, -49, 58, -102, -97, -48, -23, -89, 38, -4, 21, 106, 75, -112, -97, -55, 106, 72}),
			SNAP_BASE, "da_"
	);
	public static final Hook STORY_METADATA_GET_OBJECT = new Hook(
			/*STORY_METADATA_GET_OBJECT*/ decryptMsg(new byte[]{49, -8, 112, 41, 80, -27, -21, 57, -84, 26, 30, -93, 4, -18, -28, 122, 25, 47, -14, -120, -6, -27, 123, 35, -14, 58, 44, -64, 95, 96, -57, -70}),
			STORY_METADATA, "a", String.class
	);
	public static final Hook STORY_METADATA_BUILDER = new Hook(
			/*STORY_METADATA_BUILDER*/ decryptMsg(new byte[]{-70, -126, -74, 126, 30, 20, -99, 70, 8, -34, -71, 2, -33, 17, 79, -81, 6, -68, 28, -74, 84, -116, -111, 115, -107, -120, 11, -59, -53, 30, 103, 65}),
			STORY_METADATA_LOADER, "a", STORY_SNAP.getStrClass(), "nxy", "nxx", "mer"
	);
	public static final Hook STORY_DISPLAYED = new Hook(
			/*STORY_DISPLAYED*/ decryptMsg(new byte[]{73, -5, 110, 81, -21, 107, 44, 48, -126, -96, -16, -79, 6, -18, 116, -34}),
			STORY_ADVANCER, "C"
	);
	public static final Hook OPENED_SNAP = new Hook(
			/*OPENED_SNAP*/ decryptMsg(new byte[]{-97, -96, -110, -15, -66, -89, -32, 108, -66, -59, -62, -92, -3, 41, 7, 43}),
			RECEIVED_SNAP, "c", boolean.class
	);
	public static final Hook CONCENTRIC_TIMERVIEW_ONDRAW = new Hook(
			/*CONCENTRIC_TIMERVIEW_ONDRAW*/ decryptMsg(new byte[]{-119, -110, -37, 3, 96, 22, -37, -11, 29, -91, -54, 67, 116, 0, 100, 55, 44, -121, 41, 6, -69, 54, -40, 86, -106, -25, -5, 75, -28, 91, -28, 121}),
			CONCENTRIC_TIMERVIEW, "onDraw", Canvas.class
	);
	public static final Hook NEW_CONCENTRIC_TIMERVIEW_ONDRAW = new Hook(
			/*NEW_CONCENTRIC_TIMERVIEW_ONDRAW*/ decryptMsg(new byte[]{87, 17, -48, -51, 1, -1, 62, 36, 56, -105, 22, 74, 105, 24, -109, -28, 49, -27, -60, 41, -80, 81, -70, -121, 78, -80, -123, -14, 111, 74, -112, -89}),
			NEW_CONCENTRIC_TIMERVIEW, "onDraw", Canvas.class
	);
	public static final Hook COUNTDOWNTIMER_VIEW_ONDRAW = new Hook(
			/*COUNTDOWNTIMER_VIEW_ONDRAW*/ decryptMsg(new byte[]{-34, 96, -61, -20, 15, -126, 1, 32, 15, 117, -43, 95, -82, 96, 26, -91, 10, -29, 22, -128, 120, 95, -33, 105, -125, -32, -117, 5, 99, 127, 121, -123}),
			COUNTDOWNTIMER_VIEW, "onDraw", Canvas.class
	);
	public static final Hook GET_USERNAME = new Hook(
			/*GET_USERNAME*/ decryptMsg(new byte[]{-7, 57, -45, 26, -105, -113, 49, 125, -99, -72, 9, -15, 65, -25, -29, -80}),
			USER_PREFS, "H"
	);

	// ===========================================================================

	//public static final Hook SETTEXT_HOOK_2 = new Hook("SETTEXT_HOOK_2", ANDROID_EDITTEXT_CLASS, "setText", CharSequence.class, BufferType.class);
	//public static final Hook SETTEXT_HOOK_3 = new Hook("SETTEXT_HOOK_2", ANDROID_EDITTEXT_CLASS, "setText", CharSequence.class, BufferType.class, boolean.class, int.class);
	//public static final Hook SET_FRONT_FLASH = new Hook("SET_FRONT_FLASH", CAMERA_PARAMS, "a", Parameters.class);
	//public static final Hook GET_CAMERA_OBJECT = new Hook("GET_CAMERA_OBJECT", CAMERA_CREATION, "b", int.class);
	//public static final Hook KEY_EVENT = new Hook("KEY_EVENT", SNAP_CAPTION_VIEW, "onKeyPreIme", int.class, KeyEvent.class);
	//public static final Hook KEY_EVENT_STATIC = new Hook("KEY_EVENT_STATIC", STATIC_CAPTION_VIEW, "onKeyPreIme", int.class, KeyEvent.class);

	// CONSTRUCTORS ==============================================================


	// ===========================================================================

	public static class Hook extends Constant {
		private final HookClass hookClass;
		@Nullable private final String hookMethod;
		private final Object[] hookParams;

		Hook(String name, HookClass hookClass, @Nullable String hookMethod, Object... hookParams) {
			super(name);

			this.hookClass = hookClass;
			this.hookMethod = hookMethod;
			this.hookParams = hookParams;
		}

		// ===========================================================================

		public HookClass getHookClass() {
			return hookClass;
		}

		@Nullable public String getHookMethod() {
			return hookMethod;
		}

		public Object[] getHookParams() {
			return hookParams;
		}
	}
}
