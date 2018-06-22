package com.ljmu.andre.snaptools.ModulePack.HookDefinitions;

import android.support.annotation.NonNull;

import com.ljmu.andre.ConstantDefiner.Constant;
import com.ljmu.andre.ConstantDefiner.ConstantDefiner;
import com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.HookClass;

import static com.ljmu.andre.snaptools.Utils.StringEncryptor.decryptMsg;

/**
 * ===========================================================================
 * Hook Generation | Contains: Classes, Methods/Constructors, Variables
 * ===========================================================================
 */
@SuppressWarnings({"WeakerAccess"})
public class HookClassDef extends ConstantDefiner<HookClass> {
	public static final HookClassDef INST = new HookClassDef();

	public static final HookClass SCREENSHOT_DETECTOR = new HookClass(
			/*SCREENSHOT_DETECTOR*/ decryptMsg(new byte[]{-17, -38, -62, 73, -80, 51, 67, -4, -37, -29, -114, 81, -96, 120, -100, -93, 84, -119, 86, 48, -41, -84, -103, -110, 55, -12, 85, -47, 43, -49, -22, 90}),
			/*mtx*/ decryptMsg(new byte[]{-109, -102, -84, 110, -108, -82, 12, 32, 123, 70, -18, 111, -59, -122, 50, 44})
	);
	public static final HookClass SNAP_DATA_JSON = new HookClass(
			/*SNAP_DATA_JSON*/ decryptMsg(new byte[]{-93, 21, 88, -115, -63, 105, -59, -35, -5, 104, -29, 64, 117, -100, 76, 87}),
			/*qyt*/ decryptMsg(new byte[]{-88, 100, -84, -126, 101, -10, 96, 116, 116, -16, 100, 42, -54, 95, -42, -123})
	);
	public static final HookClass SNAP_TIMER_VIEW = new HookClass(
			/*SNAP_TIMER_VIEW*/ decryptMsg(new byte[]{81, -7, 123, -53, -61, 21, 16, 99, -23, -96, -86, -53, -34, -64, 54, 66}),
			/*com.snapchat.android.app.shared.ui.snapview.SnapTimerView*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, 106, 13, -101, -117, 116, 90, -40, 0, 123, 78, 104, -88, -61, -68, 88, -38, 10, -26, -72, -9, 79, 115, 63, 126, -58, 47, 11, -109, 82, -44, 93, -115, 123, 28, -110, -16, 7, 103, 84, -31, 97, -4, -103, -63, 90, 36, 19, -75})
	);
	public static final HookClass TEXTURE_VIDEO_VIEW = new HookClass(
			/*TEXTURE_VIDEO_VIEW*/ decryptMsg(new byte[]{-23, 114, -85, -36, 9, 50, 56, 8, 7, 32, -96, -9, 45, -115, 29, 15, 48, 22, 61, -1, -77, 13, 76, -104, -50, -3, 104, -30, 102, 19, -13, -75}),
			/*com.snapchat.opera.shared.view.TextureVideoView*/ decryptMsg(new byte[]{-89, -26, -105, -81, 43, -76, 79, 124, 13, 53, 27, -8, 79, 126, 121, 79, -54, 124, 35, -69, -99, -98, 114, 118, -87, -104, -122, 34, 17, -122, -16, 96, -72, -31, 37, -113, 45, -85, -124, -37, -2, 22, -113, 73, 39, -60, -65, -64})
	);
	public static final HookClass SNAP_COUNTDOWN_CONTROLLER = new HookClass(
			/*SNAP_COUNTDOWN_CONTROLLER*/ decryptMsg(new byte[]{69, -63, -15, 21, 49, -124, 0, -4, 115, 72, -99, -45, 67, -122, -111, 72, 47, -67, -24, -24, -71, -19, 33, 58, 78, 5, -103, 24, -65, 116, -74, -46}),
			/*lut*/ decryptMsg(new byte[]{84, -9, -38, -9, 29, 97, -21, -87, 86, -62, 124, 112, -95, 122, -105, -12})
	);
	public static final HookClass ENCRYPTION_ALGORITHM = new HookClass(
			/*ENCRYPTION_ALGORITHM*/ decryptMsg(new byte[]{95, -64, 95, 21, 52, -52, 92, -16, -33, -81, 77, -44, 74, 81, 54, -76, 26, 47, -104, 46, -32, -96, -36, -62, 71, -123, -62, -24, 36, -90, 110, -78}),
			/*com.snapchat.android.framework.crypto.CbcEncryptionAlgorithm*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, 22, 78, 101, 111, -27, 83, -27, -120, 27, -122, -66, -127, 78, 26, 117, 9, -61, -7, -80, 114, -9, 123, -98, 107, -52, -78, 61, -91, -108, -57, -45, 127, 55, 53, 124, -89, -105, 41, -56, -21, 92, 118, 73, -19, 21, 56, -19, -8})
	);
	public static final HookClass ENCRYPTED_STREAM_BUILDER = new HookClass(
			/*ENCRYPTED_STREAM_BUILDER*/ decryptMsg(new byte[]{-82, -81, -56, 25, 97, -114, 22, 84, 119, -54, -98, -14, -35, 58, 48, -43, -48, -110, -45, -34, 120, -13, 63, 45, 100, -102, 17, -47, 56, 85, 114, -101}),
			/*nou*/ decryptMsg(new byte[]{-34, -103, 75, -35, 87, -111, -92, 38, -88, -1, 29, 13, -128, -54, 9, 40})
	);
	public static final HookClass STORY_METADATA = new HookClass(
			/*STORY_METADATA*/ decryptMsg(new byte[]{-4, -25, 114, 16, 71, 20, -43, 105, -8, 20, 123, -105, -56, -11, 6, -17}),
			/*qjd*/ decryptMsg(new byte[]{-126, 99, -57, -66, -51, -76, -104, -62, 13, 92, 52, -60, -65, 63, 33, -19})
	);
	public static final HookClass SNAP_BASE = new HookClass(
			/*SNAP_BASE*/ decryptMsg(new byte[]{-83, 10, -15, 71, -24, 49, 101, 73, 0, -87, 7, -72, -7, 98, -7, -40}),
			/*jac*/ decryptMsg(new byte[]{-56, -118, -40, -106, -4, -33, -122, 99, -35, -57, 58, -41, 64, -29, -78, 16})
	);
	public static final HookClass SNAP_STATUS = new HookClass(
			/*SNAP_STATUS*/ decryptMsg(new byte[]{63, -106, 13, 61, -78, -19, 25, -113, -70, -64, -96, -55, -65, 80, 22, -59}),
			/*jac$a*/ decryptMsg(new byte[]{121, -69, -11, 76, -103, 106, -81, -124, -60, -117, -85, 82, 9, 101, -15, 111})
	);
	public static final HookClass RECEIVED_SNAP = new HookClass(
			/*RECEIVED_SNAP*/ decryptMsg(new byte[]{114, 25, 89, -22, 28, -103, -19, 78, 2, -92, -19, -91, -56, 0, -5, 82}),
			/*mlv*/ decryptMsg(new byte[]{-100, -93, 94, -109, 29, -6, -109, 95, 90, -80, -83, -85, -81, -37, -106, 81})
	);
	public static final HookClass RECEIVED_SNAP_ENCRYPTION = new HookClass(
			/*RECEIVED_SNAP_ENCRYPTION*/ decryptMsg(new byte[]{-39, 28, -56, 11, 22, 62, 121, -100, 49, 124, -87, -60, 114, -68, -9, 22, 76, 73, -35, -87, 48, -73, -6, 86, -92, -20, 7, 49, 86, 120, -15, -30}),
			/*gvg*/ decryptMsg(new byte[]{68, 103, -21, -98, 28, 111, -13, 103, -86, 89, 91, -31, -33, -55, 65, 18})
	);
	public static final HookClass RECEIVED_SNAP_PAYLOAD_BUILDER = new HookClass(
			/*RECEIVED_SNAP_PAYLOAD_BUILDER*/ decryptMsg(new byte[]{98, -94, 121, 124, -100, -96, 40, -16, -64, -51, -80, -68, 56, 76, 127, -42, 34, -24, 62, -24, 70, -119, 60, 127, -16, 2, -117, 52, 14, -26, 102, -121}),
			/*ctp*/ decryptMsg(new byte[]{62, 89, -125, 124, 76, -42, -111, 80, 12, 24, -107, 127, 46, 125, -99, 46})
	);
	public static final HookClass STORY_SNAP_PAYLOAD_BUILDER = new HookClass(
			/*STORY_SNAP_PAYLOAD_BUILDER*/ decryptMsg(new byte[]{100, -68, 31, -7, -51, 31, 85, 68, -8, 104, 93, 55, 100, 4, 94, -3, -55, 106, 29, 18, -16, 39, -125, 114, -102, -65, -26, -54, -13, 39, -99, -53}),
			/*das*/ decryptMsg(new byte[]{-47, -116, -104, -9, 121, 52, -122, 56, 15, -89, -7, 127, -87, -102, 4, -115})
	);
	public static final HookClass GROUP_SNAP_METADATA = new HookClass(
			/*GROUP_SNAP_METADATA*/ decryptMsg(new byte[]{-93, 86, -68, -65, 80, -110, 9, -109, -112, -32, -8, -33, -22, 2, 101, 54, -60, -33, -90, 10, -76, -27, 116, 126, 59, 64, 122, 42, -102, -87, -91, 29}),
			/*ils*/ decryptMsg(new byte[]{-75, -26, 122, -59, -31, 12, -42, 97, -67, -7, -49, -95, 102, 33, -7, 115})
	);
	public static final HookClass STORY_SNAP = new HookClass(
			/*STORY_SNAP*/ decryptMsg(new byte[]{-17, -14, -86, -109, 88, 89, -125, -120, -20, -125, 92, -105, -4, 114, 9, 9}),
			/*mes*/ decryptMsg(new byte[]{-41, 65, 116, -101, -109, -34, 101, -112, -39, 79, -88, 93, 99, 3, 105, 125})
	);
	public static final HookClass STORY_STATUS_UPDATER = new HookClass(
			/*STORY_STATUS_UPDATER*/ decryptMsg(new byte[]{71, -4, -108, 44, 80, 3, 82, -13, 32, 6, 5, 99, -84, -83, -10, -72, -119, -84, 95, -27, 15, 31, 80, 17, 45, 13, 64, -22, 42, -8, -86, 39}),
			/*dbc*/ decryptMsg(new byte[]{43, -113, -89, -73, -25, 64, -123, 4, -34, -29, 19, -45, -87, 8, -124, 102})
	);
	public static final HookClass STORY_METADATA_LOADER = new HookClass(
			/*STORY_METADATA_LOADER*/ decryptMsg(new byte[]{-111, 7, -41, 99, -50, -68, -128, -39, -99, 127, -55, -29, 87, -70, -110, -35, -124, -19, 39, -76, -121, -73, -17, -112, -23, 0, -28, -64, -120, -33, 1, -18}),
			/*dki*/ decryptMsg(new byte[]{-5, -52, -12, 69, -66, 126, -34, -70, -53, -80, 110, -2, -88, -15, 2, -48})
	);
	public static final HookClass STORY_ADVANCER = new HookClass(
			/*STORY_ADVANCER*/ decryptMsg(new byte[]{105, 20, -16, 104, -75, -123, -26, 14, -13, 118, -89, -56, -39, 11, -11, -55}),
			/*qds*/ decryptMsg(new byte[]{-101, 92, -94, 84, 20, -27, -45, -65, 41, 108, 55, 108, -103, -18, 57, -90})
	);
	public static final HookClass SENT_IMAGE = new HookClass(
			/*SENT_IMAGE*/ decryptMsg(new byte[]{-78, 70, 29, 103, -80, -128, -43, 118, 127, 78, 114, 32, -54, 89, 94, 25}),
			/*mly*/ decryptMsg(new byte[]{53, -46, 95, 20, -65, 81, 105, -1, -10, -127, -14, -105, 28, 102, 43, 40})
	);
	public static final HookClass SENT_VIDEO = new HookClass(
			/*SENT_VIDEO*/ decryptMsg(new byte[]{-1, 86, -110, -121, -59, -88, 1, -34, -123, 1, -96, 57, 5, 54, -57, -108}),
			/*mmn*/ decryptMsg(new byte[]{-102, -93, 85, -20, 109, -3, 51, 84, 56, -108, -29, 12, -23, 43, -124, -122})
	);
	public static final HookClass SENT_BATCHED_VIDEO = new HookClass(
			/*SENT_BATCHED_VIDEO*/ decryptMsg(new byte[]{10, -125, 35, -42, 35, -31, 107, -43, -60, -10, -51, -44, -105, -90, 73, -91, 100, -80, 122, 4, -58, -32, 92, -127, -52, 103, -100, 122, 106, -24, 116, -83}),
			/*dvq*/ decryptMsg(new byte[]{82, -124, -88, -23, 52, -108, -79, 103, -80, -86, 64, 110, 42, -84, -84, 97})
	);
	public static final HookClass ENUM_BATCHED_SNAP_POSITION = new HookClass(
			/*ENUM_BATCHED_SNAP_POSITION*/ decryptMsg(new byte[]{53, 0, -87, 36, 13, -57, 121, -88, 74, -94, 8, 9, -20, 110, 61, -118, -83, -35, 46, -14, 115, -124, -28, 99, 24, 21, 57, -109, 49, -34, -66, -6}),
			/*bzl*/ decryptMsg(new byte[]{-27, -95, -11, 78, 91, 103, 9, -15, -96, -9, 115, 19, -49, -122, 99, -65})
	);
	public static final HookClass SENT_SNAP_BASE = new HookClass(
			/*SENT_SNAP_BASE*/ decryptMsg(new byte[]{91, 57, 89, 39, -104, -90, -79, 125, 124, 91, 102, -52, 15, 41, 87, -67}),
			/*mlc*/ decryptMsg(new byte[]{121, 8, 14, 107, -45, -42, -43, -113, 125, -87, -25, -68, 18, -45, -64, -4})
	);
	public static final HookClass META_DATA_BUILDER = new HookClass(
			/*META_DATA_BUILDER*/ decryptMsg(new byte[]{-59, 47, 9, -52, -94, -66, 72, 110, -27, 119, -4, -56, 97, -93, 73, 3, -37, 44, 36, -91, -79, -96, 61, 47, -119, 89, 86, 113, 117, 99, 31, 29}),
			/*jhc*/ decryptMsg(new byte[]{6, 121, 11, -77, -102, -72, -69, -9, 14, 24, -122, -12, -107, 87, -95, -126})
	);
	public static final HookClass GALLERY_SNAP = new HookClass(
			/*GALLERY_SNAP*/ decryptMsg(new byte[]{-126, -108, -70, -121, 123, -106, 96, 11, -112, 11, 48, -45, -65, 28, -104, 66}),
			/*fot*/ decryptMsg(new byte[]{96, -44, 90, -123, 125, -68, 78, -26, 86, 89, 46, -11, 21, 108, 11, -96})
	);
	public static final HookClass CHAT_IMAGE_METADATA = new HookClass(
			/*CHAT_IMAGE_METADATA*/ decryptMsg(new byte[]{74, -55, -51, 15, -94, -114, -15, 69, 48, -66, -59, -43, -87, 64, -53, 71, -60, -33, -90, 10, -76, -27, 116, 126, 59, 64, 122, 42, -102, -87, -91, 29}),
			/*ilj*/ decryptMsg(new byte[]{54, 38, -94, -57, 71, 30, -121, -53, -107, 14, 107, 0, 67, 10, 45, 33})
	);
	public static final HookClass CHAT_VIDEO_METADATA = new HookClass(
			/*CHAT_VIDEO_METADATA*/ decryptMsg(new byte[]{119, -7, -48, -4, 58, 86, 87, 96, -33, 116, 12, -9, -40, 126, -103, -63, -60, -33, -90, 10, -76, -27, 116, 126, 59, 64, 122, 42, -102, -87, -91, 29}),
			/*jct*/ decryptMsg(new byte[]{-91, 31, -26, -96, 104, -58, -111, 15, 60, 24, -33, -2, 61, 25, 109, 101})
	);
	public static final HookClass CHAT_VIDEO = new HookClass(
			/*CHAT_VIDEO*/ decryptMsg(new byte[]{-94, -120, 13, 64, 102, 117, -72, -114, 9, -11, -90, 123, -96, -44, 28, 41}),
			/*iwy*/ decryptMsg(new byte[]{77, -120, -57, -124, -82, 53, -65, 90, -126, 52, 2, 81, -68, 117, 36, 59})
	);
	public static final HookClass CONCENTRIC_TIMERVIEW = new HookClass(
			/*CONCENTRIC_TIMERVIEW*/ decryptMsg(new byte[]{-119, -110, -37, 3, 96, 22, -37, -11, 29, -91, -54, 67, 116, 0, 100, 55, -119, 22, -103, -67, -92, 91, 125, 94, 14, 99, 78, -67, 95, -89, -110, 115}),
			/*com.snapchat.android.framework.ui.views.ConcentricTimerView*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, -68, -48, 5, -89, -122, 21, -35, -19, 112, -97, -80, 106, -15, -16, 82, 76, -49, -112, -74, -32, 31, 86, -38, 56, 81, -102, -62, 110, -64, 87, -25, -1, 31, 76, -93, 83, -126, 1, -84, -32, 59, 77, 51, 22, 106, -113, -109, -104})
	);
	public static final HookClass NEW_CONCENTRIC_TIMERVIEW = new HookClass(
			/*NEW_CONCENTRIC_TIMERVIEW*/ decryptMsg(new byte[]{87, 17, -48, -51, 1, -1, 62, 36, 56, -105, 22, 74, 105, 24, -109, -28, -16, -83, 50, -14, -19, 25, 109, -63, 116, 9, 110, 26, 41, -92, -13, -99}),
			/*com.snapchat.android.framework.ui.views.NewConcentricTimerView*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, -68, -48, 5, -89, -122, 21, -35, -19, 112, -97, -80, 106, -15, -16, 82, 76, -116, 92, 45, 124, -14, -4, -57, 73, 36, -55, -79, 22, 19, 37, 31, 114, 40, 26, 76, 46, -69, 31, -8, -63, 96, 125, -87, 76, 46, 54, -57, -74})
	);
	public static final HookClass COUNTDOWNTIMER_VIEW = new HookClass(
			/*COUNTDOWNTIMER_VIEW*/ decryptMsg(new byte[]{-34, 96, -61, -20, 15, -126, 1, 32, 15, 117, -43, 95, -82, 96, 26, -91, -15, -54, -9, -31, 119, -2, -31, 47, -79, -103, -16, 50, -38, -32, 80, -120}),
			/*com.snapchat.android.framework.ui.views.CountdownTimerView*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, -68, -48, 5, -89, -122, 21, -35, -19, 112, -97, -80, 106, -15, -16, 82, 76, -15, -69, -2, 46, 73, -25, 98, 122, -64, 15, 85, 10, 34, -99, -41, -88, 107, 47, -53, -28, 95, -54, -100, -38, 74, 7, -10, 18, 96, -105, 62, -125})
	);
	public static final HookClass LENS_AUTHENTICATION = new HookClass(
			/*LENS_AUTHENTICATION*/ decryptMsg(new byte[]{-2, -122, 36, -116, 52, -70, 55, 89, -6, -76, -101, 17, 47, 17, 59, 81, 90, 124, 9, -127, 53, 120, -19, -36, 78, 29, -75, -73, -3, 19, 51, -113}),
			/*hnq*/ decryptMsg(new byte[]{50, -20, 71, -20, 107, -128, -52, 19, 71, -123, -45, -96, 9, 114, -72, -21})
	);
	public static final HookClass ENUM_SNAP_ADVANCE_MODES = new HookClass(
			/*ENUM_SNAP_ADVANCE_MODES*/ decryptMsg(new byte[]{-109, 58, 26, -106, 62, -10, -114, -107, 25, 103, -68, -29, 105, -95, -13, 104, -111, 106, -122, 32, 95, -33, 21, 102, 84, -107, 103, -63, 4, 117, 40, -44}),
			/*qgc*/ decryptMsg(new byte[]{48, 45, -125, -57, -57, 28, 26, -104, -30, -37, -90, 120, 48, -49, -98, -74})
	);
	public static final HookClass LENS = new HookClass(
			/*LENS*/ decryptMsg(new byte[]{84, 42, -89, 49, 104, 39, -23, -53, -4, 38, -58, -121, -65, 27, -62, 42}),
			/*hlw*/ decryptMsg(new byte[]{50, 71, -48, -77, -37, -91, 16, -89, -87, 23, -89, 71, 33, 49, -107, 92})
	);
	public static final HookClass LENS_ASSET_SERIALISER = new HookClass(
			/*LENS_ASSET_SERIALISER*/ decryptMsg(new byte[]{-97, 17, -124, 59, -33, -102, -71, 46, 53, -28, 39, 127, 34, -119, 108, -77, 52, -44, -9, 118, 18, -103, 58, -11, 71, -74, -107, -108, -32, 3, -29, 25}),
			/*qvx*/ decryptMsg(new byte[]{9, 23, -51, 78, -70, 32, 121, 126, 125, -66, -49, 47, -115, -41, -24, 90})
	);
	public static final HookClass LENS_CATEGORY_RESOLVER = new HookClass(
			/*LENS_CATEGORY_BUILDER*/ decryptMsg(new byte[]{107, -84, -108, -19, 26, 68, -103, 19, -103, 43, 122, -34, -46, -85, 68, -8, 13, -12, -43, -87, -87, -34, -59, 46, -12, 118, 124, 41, 74, 35, -104, -20}),
			/*hlz*/ decryptMsg(new byte[]{30, 21, -24, 35, 125, 84, 51, 83, -89, -87, -83, 45, 80, -16, 106, -106})
	);
	public static final HookClass LENS_LOADER = new HookClass(
			/*LENS_LOADER*/ decryptMsg(new byte[]{113, 68, 96, -12, 126, 102, 102, -122, 112, 84, -95, -97, 88, 116, -97, -112}),
			/*hmu*/ decryptMsg(new byte[]{-17, -67, -28, 41, 122, -15, 57, -6, -20, -99, 86, 82, -39, 20, -30, 7})
	);
	public static final HookClass STORY_LOADER = new HookClass(
			/*STORY_LOADER*/ decryptMsg(new byte[]{-45, -109, -60, 5, 51, 10, -64, -26, -25, 3, -40, 48, 114, 4, 11, 21}),
			/*cwg*/ decryptMsg(new byte[]{-100, -62, 83, 57, -22, 106, 86, 84, -116, 113, -119, 114, -117, -2, -43, -81})
	);
	public static final HookClass STORY_SPONSORED = new HookClass(
			/*STORY_ADVERT*/ decryptMsg(new byte[]{-86, -31, 62, -48, 43, 71, 32, -1, 89, -6, 93, -122, -121, 120, 100, -118}),
			/*dja*/ decryptMsg(new byte[]{-56, -84, 82, 57, -78, -30, 4, 82, -74, -122, 64, -47, 4, 72, 11, -76})
	);
	public static final HookClass STORY_FRIEND_RECENT = new HookClass(
			/*STORY_FRIEND_RECENT*/ decryptMsg(new byte[]{-6, 50, -27, -84, -97, 87, 57, -82, 126, -76, 45, -32, -121, -7, -82, 62, -72, -77, 57, 70, 52, 94, 36, -73, -112, 18, 42, -123, 42, 85, 21, 61}),
			/*dho*/ decryptMsg(new byte[]{24, -117, -97, -61, -109, 34, 71, 14, 49, 103, -2, 99, 27, 120, -97, -128})
	);
	public static final HookClass STORY_FRIEND_VIEWED = new HookClass(
			/*STORY_FRIEND_VIEWED*/ decryptMsg(new byte[]{-26, 35, 4, -109, 107, 78, -46, -52, 94, 107, 52, -96, -89, -15, 103, -103, -6, -93, -13, 9, 20, 20, -74, 99, 118, 119, 75, 2, -26, -111, -91, 28}),
			/*dgr*/ decryptMsg(new byte[]{-125, 95, 91, 98, 90, -88, -22, 102, -109, -90, -28, -44, 90, -106, 25, 65})
	);
	public static final HookClass STORY_SNAP_AD_LOADER = new HookClass(
			/*STORY_SNAP_AD_LOADER*/ decryptMsg(new byte[]{-50, 46, -111, -88, -75, 5, 7, -18, -12, -40, 66, 57, 103, 122, -28, 10, 123, 16, 48, 90, 21, 108, 105, -27, 114, 87, -115, 55, 113, -30, -108, -15}),
			/*czh*/ decryptMsg(new byte[]{-106, -80, -127, -94, -97, -60, 67, -78, 18, 83, 17, 122, 108, -83, -37, -121})
	);
	public static final HookClass FRIEND_PROFILE_POPUP_FRAGMENT = new HookClass(
			/*FRIEND_PROFILE_POPUP_FRAGMENT*/ decryptMsg(new byte[]{59, -52, -36, -28, 33, 44, -117, -10, 73, -65, -47, -69, 17, -5, 116, 74, -24, 85, -109, 8, 114, -103, -78, -31, -38, 115, -38, -73, 12, 127, 59, -18}),
			/*com.snapchat.android.app.feature.miniprofile.internal.friend.NewFriendMiniProfilePopupFragment*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, -42, 19, 50, -101, -37, 44, 18, -49, -107, -26, 49, 14, -123, -33, -97, -79, 74, 109, 0, -84, -58, -99, 55, 122, -45, -43, 95, 84, 5, 82, 13, 46, 83, -86, -103, -71, -76, 126, -109, -112, -33, -112, 55, 111, -12, -33, 100, -18, -97, -93, 45, 95, -78, -84, 21, 90, -54, 43, -79, 7, -13, 111, -66, 118, -73, 4, -68, 122, 16, 30, -78, -19, -7, 77, -67, -115, 15, 92, 53, 125})
	);
	//public static final HookClass STATIC_CAPTION_VIEW = new HookClass("STATIC_CAPTION_VIEW", "com.snapchat.android.app.feature.creativetools.caption.StaticCaptionView");
	public static final HookClass USER_PREFS = new HookClass(
			/*USER_PREFS*/ decryptMsg(new byte[]{20, 86, -128, 116, -108, -62, -85, 89, 47, -98, -40, -48, 20, 3, -97, -25}),
			/*com.snapchat.android.core.user.UserPrefs*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, 66, -101, 14, 112, -29, 86, -32, -117, -16, 101, -67, 47, 34, -59, -16, -27, 68, -105, -3, -67, -19, -114, 38, -32, -110, -70, -81, -116, 61, -7, -70, -102})
	);
	public static final HookClass CAMERA_FRAGMENT = new HookClass(
			/*CAMERA_FRAGMENT*/ decryptMsg(new byte[]{-103, 61, -71, -120, -10, -53, -21, -116, -95, -37, 65, -15, -56, -85, 56, -86}),
			/*com.snapchat.android.app.main.camera.CameraFragment*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, -8, 84, 127, -51, -35, -37, 127, 73, -89, 15, 19, 40, 39, -96, 21, 54, 117, -53, 104, -89, -63, -51, -76, 7, -125, -81, 49, -66, 57, 15, 96, 67, -20, 112, 82, -83, -5, -84, -24, 49, 90, -87, -24, -68, -125, -75, -96, 85})
	);
	public static final HookClass ENUM_LENS_TYPE = new HookClass(
			/*ENUM_LENS_TYPE*/ decryptMsg(new byte[]{44, 38, -51, -116, -83, -43, 19, -40, -105, -78, -23, -107, 80, 101, 41, -50}),
			/*hlw$a*/ decryptMsg(new byte[]{25, 118, 13, -106, 121, -79, 45, -125, 67, -98, -123, 71, -91, 119, 24, -117})
	);
	public static final HookClass LENS_CATEGORY = new HookClass(
			/*LENS_CATEGORY*/ decryptMsg(new byte[]{106, 40, 31, 69, 15, -45, 46, -40, 18, 101, -7, 91, -47, 78, -46, -124}),
			/*hmb*/ decryptMsg(new byte[]{-50, 89, 3, 30, 122, -71, 69, -26, -24, -84, -80, 20, -46, 48, 25, 42})
	);
	public static final HookClass LENS_SLUG = new HookClass(
			/*LENS_SLUG*/ decryptMsg(new byte[]{-28, 22, -51, -77, -123, -86, 59, -54, -28, -118, -75, -113, -50, 34, 92, -35}),
			/*rwh*/ decryptMsg(new byte[]{65, 112, -107, -33, 7, 3, 4, -18, 50, 94, -67, 37, 67, -5, -77, -113})
	);
	public static final HookClass LENS_TRACK = new HookClass(
			/*LENS_TRACK*/ decryptMsg(new byte[]{-80, 53, 33, 58, -63, -53, -27, -24, -119, -53, -98, 31, 13, -117, -52, -116}),
			/*sbo*/ decryptMsg(new byte[]{-31, 51, 105, -51, -46, -26, -17, 32, 37, -90, 54, 110, 43, -40, 83, 63})
	);
	public static final HookClass LENS_ASSET = new HookClass(
			/*LENS_ASSET*/ decryptMsg(new byte[]{103, -30, 45, 86, 2, -11, -90, -118, -30, -38, 18, -82, -100, 100, 81, -9}),
			/*qvy*/ decryptMsg(new byte[]{59, -101, -61, 47, 114, 126, 100, -69, -33, 38, -46, 109, -46, 5, -107, 12})
	);
	public static final HookClass LENS_ASSET_BUILT = new HookClass(
			/*LENS_ASSET_BUILT*/ decryptMsg(new byte[]{-45, -43, -18, 76, -1, 48, 54, -122, 37, -128, 10, -37, 31, -22, -106, -83, 104, -50, 116, -85, 125, 59, 23, -57, 29, -117, 35, 62, -41, 17, -14, 66}),
			/*hlx*/ decryptMsg(new byte[]{124, -119, 112, 107, -59, -107, -72, 111, 48, 87, 89, 106, -126, 105, -52, -53})
	);
	public static final HookClass LENS_ASSET_TYPE = new HookClass(
			/*LENS_ASSET_TYPE*/ decryptMsg(new byte[]{110, 33, -86, -45, -113, 3, 79, 127, 101, 90, -109, -73, 121, -122, 122, -6}),
			/*hlx$a*/ decryptMsg(new byte[]{121, -63, 90, -115, 93, 23, -85, 46, 39, -97, -4, 5, -45, -110, 6, 97})
	);
	public static final HookClass LENS_ASSET_LOAD_MODE = new HookClass(
			/*LENS_ASSET_LOAD_MODE*/ decryptMsg(new byte[]{-93, 111, 1, -111, -79, 106, 96, -79, 92, -75, -71, -107, -102, -81, 59, -50, -16, 117, -15, 77, -73, -67, -116, -15, 88, 36, -63, -19, -33, -101, 23, 11}),
			/*hmi*/ decryptMsg(new byte[]{48, -43, -47, -60, 107, 55, 107, 21, -76, -84, -39, 5, -76, 85, 106, -39})
	);
	public static final HookClass ENUM_LENS_ACTIVATOR_TYPE = new HookClass(
			/*ENUM_LENS_ACTIVATOR_TYPE*/ decryptMsg(new byte[]{-99, 118, -2, -54, -32, 122, -109, -76, 122, 42, -62, -4, -71, 32, -90, -113, 74, -29, -81, 81, 48, 91, -104, 18, 6, -9, -98, -37, -8, -6, 117, -124}),
			/*com.looksery.sdk.domain.Category.ActivatorType*/ decryptMsg(new byte[]{71, -101, 101, 27, 20, -56, 122, 30, 43, 90, -67, -91, -12, -75, 106, -90, 89, 60, -79, 45, -100, -36, -98, -33, -62, -9, 65, -100, 109, -98, -117, -112, -69, 103, -15, 100, 85, -87, 36, -67, -98, 102, 127, 31, -126, -25, 108, -80})
	);
	//public static final HookClass FONT_CLASS = new HookClass("FONT_CLASS", "ecw");
	public static final HookClass FONT_CLASS = new HookClass(
			/*FONT_CLASS*/ decryptMsg(new byte[]{-65, -67, -63, -79, 70, 113, 106, -8, -1, -46, 30, -2, 21, 46, 40, 28}),
			/*android.graphics.Typeface*/ decryptMsg(new byte[]{118, 60, 26, 66, -105, -108, -74, -67, 48, 82, -14, -127, 65, -112, -60, -81, 26, 45, 102, -102, -67, 39, -84, 50, -126, 115, -66, 34, -103, -46, -49, -89})
	);
	public static final HookClass SNAPCHAT_CAPTION_VIEW_CLASS = new HookClass(
			/*SNAPCHAT_CAPTION_VIEW_CLASS*/ decryptMsg(new byte[]{-6, -105, -105, -84, 18, 0, 117, -13, 14, -54, -31, -80, -95, 26, 123, -37, 15, -2, 27, -34, 16, -65, 20, 104, 49, -83, 23, -84, -7, 5, 70, -8}),
			/*com.snapchat.android.app.feature.creativetools.caption.SnapCaptionView*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, -42, 19, 50, -101, -37, 44, 18, -49, -107, -26, 49, 14, -123, -33, -97, -79, 77, 39, 98, 44, -46, -76, 74, -107, 94, -117, 41, 101, -17, -68, -38, 99, -20, -11, -43, -56, -13, -21, -14, 18, 65, -49, 36, 25, -93, -60, -112, -31, 120, 57, 55, -54, -86, 106, -118, -71, -16, -8, 21, -53, 61, -82, -31, 28})
	);
	public static final HookClass CAPTION_MANAGER_CLASS = new HookClass(
			/*CAPTION_MANAGER_CLASS*/ decryptMsg(new byte[]{77, 81, -95, 116, -94, 14, -14, -126, 108, 98, -119, 102, -112, 81, -55, -28, 48, 76, 105, 30, 120, 31, -85, 9, 1, -18, -79, -34, -43, 101, -89, 119}),
			/*ecs*/ decryptMsg(new byte[]{4, -78, -15, -97, 119, 114, 108, 14, 28, -13, 19, -39, -87, -125, -122, 22})
	);
	/*public static final HookClass SHARED_MEDIA_BUILDER = new HookClass(
			/*SHARED_MEDIA_BUILDER decryptMsg(new byte[]{-120, -11, 20, -31, -86, 66, 79, -42, -120, 13, 76, 89, 39, -60, 118, -98, 125, -8, 35, -61, 66, -56, -55, 126, -63, -110, -104, -79, 52, 41, -106, 66}),
			/*ioa$a decryptMsg(new byte[]{114, -83, 115, 22, 21, 4, -88, 65, -118, 96, 100, 122, -42, 105, 5, -97})
	);*/
	public static final HookClass CHAT_MESSAGE_VIEW_HOLDER = new HookClass(
			/*CHAT_MESSAGE_VIEW_HOLDER*/ decryptMsg(new byte[]{-12, 90, 80, -45, 77, -126, -20, -83, -116, -112, 116, 116, -78, -98, 60, 126, 112, -11, 113, -66, 32, 88, 112, -10, -90, -81, 103, -71, -14, -23, -75, -107}),
			/*iud*/ decryptMsg(new byte[]{5, 1, -73, 117, -114, 117, -117, 35, -21, 124, 92, -38, 8, -2, 124, 125})
	);
	public static final HookClass OPERA_PAGE_VIEW = new HookClass(
			/*OPERA_PAGE_VIEW*/ decryptMsg(new byte[]{-19, -47, 39, -74, -103, 41, -58, 23, -46, 79, -58, 13, -39, -108, 54, 125}),
			/*com.snapchat.opera.view.OperaPageView*/ decryptMsg(new byte[]{-89, -26, -105, -81, 43, -76, 79, 124, 13, 53, 27, -8, 79, 126, 121, 79, 83, 23, -97, 37, 46, -110, 60, -83, -99, 88, 70, 96, -88, -8, -79, -76, -25, 87, -97, -38, 53, -78, -76, -11, 100, 52, 8, 21, 94, -50, 49, -89})
	);
	public static final HookClass CHAT_METADATA_JSON_PARSER = new HookClass(
			/*CHAT_METADATA_JSON_PARSER*/ decryptMsg(new byte[]{-68, -114, 1, -65, -66, -102, -82, -26, -15, 89, -95, 121, 102, 112, 80, -68, -114, 84, 105, 83, -92, -2, 63, -12, -51, -53, 12, 78, 63, -79, -20, 76}),
			/*qya*/ decryptMsg(new byte[]{-121, -22, -3, -92, 122, 57, 56, 83, 59, 101, -104, -108, 72, 26, -111, -78})
	);
	public static final HookClass CHAT_METADATA = new HookClass(
			/*CHAT_METADATA*/ decryptMsg(new byte[]{100, 49, 8, -114, -126, -62, -6, 62, -110, 59, 6, 89, -13, -20, 32, 54}),
			/*qye*/ decryptMsg(new byte[]{94, 40, -7, 72, 116, -40, -107, 1, -42, 24, -61, 91, 89, -84, -127, -126})
	);
	public static final HookClass CHAT_HEADER_METADATA = new HookClass(
			/*CHAT_HEADER_METADATA*/ decryptMsg(new byte[]{4, -86, -7, -48, -116, -103, 118, 32, -22, -64, 19, -91, -8, 39, -26, 81, 92, 65, 125, 123, -4, -42, -49, -8, 73, 110, 1, -77, 18, -55, 111, 95}),
			/*rhg*/ decryptMsg(new byte[]{123, 90, -18, -24, -43, 77, -127, -22, -24, 108, -77, -21, 100, -26, 7, -92})
	);
	public static final HookClass CHAT_BODY_METADATA = new HookClass(
			/*CHAT_BODY_METADATA*/ decryptMsg(new byte[]{-72, 87, -108, 19, 27, 44, -128, 89, -6, -32, 115, -14, -95, 58, 42, -40, 81, 87, -114, -11, -36, 97, -20, 91, -29, 4, 54, -41, -72, -37, 17, -57}),
			/*rmt*/ decryptMsg(new byte[]{-76, 98, 62, 76, -108, -93, 42, -34, 82, -97, -123, 90, 44, 66, 31, 0})
	);
	public static final HookClass GEOFILTER_VIEW = new HookClass(
			/*GEOFILTER_VIEW*/ decryptMsg(new byte[]{88, -87, 2, 67, -2, -87, 22, 28, -94, 33, -40, 3, -48, -35, 118, -40}),
			/*com.snapchat.android.app.shared.feature.preview.ui.smartfilters.GeofilterView*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, 106, 13, -101, -117, 116, 90, -40, 0, 123, 78, 104, -88, -61, -68, 88, -38, -47, -108, -108, -68, -83, -50, 103, -38, 111, -85, -48, -96, -126, 89, -63, -46, -121, 106, -3, -55, -48, 126, 53, 47, -65, 96, -12, -53, 69, -27, -122, -27, 116, 92, -46, 78, 38, -16, 65, -94, 82, 102, 87, -5, 84, -96, -94, -108})
	);
	public static final HookClass FILTER_METADATA = new HookClass(
			/*FILTER_METADATA*/ decryptMsg(new byte[]{-17, -5, -106, -47, 74, 47, 3, -85, 45, 32, -71, -128, -20, -118, -92, -58}),
			/*mfx*/ decryptMsg(new byte[]{-24, 106, -53, -21, 98, -89, -104, -4, 93, 17, 64, -59, 126, 121, -88, 56})
	);
	public static final HookClass FILTER_METADATA_LOADER = new HookClass(
			/*FILTER_METADATA_LOADER*/ decryptMsg(new byte[]{54, -48, 117, 63, -48, 81, -62, -2, -78, 1, -30, -50, -119, 94, 43, -44, 48, -51, -81, 124, 49, -113, 57, -65, -60, -48, 26, 31, -106, -9, -86, 10}),
			/*eib*/ decryptMsg(new byte[]{6, 50, -89, 91, -127, 108, 59, -38, -41, -46, 48, -76, -45, -37, 111, -9})
	);
	public static final HookClass FILTER_METADATA_CREATOR = new HookClass(
			/*FILTER_METADATA_CREATOR*/ decryptMsg(new byte[]{54, -48, 117, 63, -48, 81, -62, -2, -78, 1, -30, -50, -119, 94, 43, -44, -29, -83, 110, -29, 104, 33, -61, -9, 106, -15, -90, -65, -80, 27, -47, 111}),
			/*mgr$2*/ decryptMsg(new byte[]{-84, -90, -15, -24, 100, -77, 9, 57, 4, 56, -59, 51, -38, 67, -65, -103})
	);
	public static final HookClass GEOFILTER_VIEW_CREATOR = new HookClass(
			/*GEOFILTER_VIEW_CREATOR*/ decryptMsg(new byte[]{32, 125, 21, -111, -61, -13, 26, -48, 54, 123, 105, 86, 24, -121, -34, -108, -8, 100, -81, -17, 39, 29, 80, -17, -3, -122, -6, -88, -103, 31, -32, -43}),
			/*ehh*/ decryptMsg(new byte[]{-77, 91, -9, -88, 14, -51, -36, -62, 2, 24, 89, 103, -73, -84, -121, -48})
	);
	public static final HookClass CHAT_NOTIFICATION_CREATOR = new HookClass(
			/*CHAT_NOTIFICATION_CREATOR*/ decryptMsg(new byte[]{-55, 31, -60, 93, 69, -20, 14, -13, -110, -101, 29, 30, 21, -87, 39, 72, -101, -4, 91, -119, 113, -123, -28, 86, 119, 35, -61, 43, 80, 65, -113, -69}),
			/*hzy*/ decryptMsg(new byte[]{-81, 43, 48, 110, 57, -71, 85, -8, -114, 19, 43, -11, 7, 90, -75, -48})
	);
	public static final HookClass NETWORK_MANAGER = new HookClass(
			/*NETWORK_MANAGER*/ decryptMsg(new byte[]{-71, -94, -40, 86, 62, -76, 75, -14, 37, 123, -25, 34, 0, -5, 75, -64}),
			/*nst*/ decryptMsg(new byte[]{1, 92, -49, -2, 78, -10, 113, -120, -80, -36, 20, 107, 109, 85, -124, 113})
	);
	public static final HookClass NETWORK_DISPATCHER = new HookClass(
			/*NETWORK_DISPATCHER*/ decryptMsg(new byte[]{94, 31, -100, 59, -112, 116, 125, 61, -117, 59, 124, -49, -117, -47, -66, -10, 98, 54, -70, 82, -48, -51, 86, -1, 23, 51, 120, -94, 4, -75, 98, -27}),
			/*icj*/ decryptMsg(new byte[]{16, -43, 110, 96, 100, -26, -10, -43, 113, -3, 42, 75, 100, 57, -93, 77})
	);
	public static final HookClass CHAT_MESSAGE_METADATA = new HookClass(
			/*CHAT_MESSAGE_METADATA*/ decryptMsg(new byte[]{-50, 44, 120, -82, 113, -65, 81, -120, 5, -126, -76, -93, 71, -33, -116, -17, 35, 108, 78, -36, 77, -82, 21, 99, -117, 72, 87, 32, 49, 74, 108, -30}),
			/*iwh*/ decryptMsg(new byte[]{21, 110, 105, 78, -119, -19, -91, -20, -39, -83, -103, -78, -67, 1, -94, 111})
	);
	public static final HookClass CHAT_V3_FRAGMENT = new HookClass(
			/*CHAT_V3_FRAGMENT*/ decryptMsg(new byte[]{60, 22, -104, 60, 102, 6, 52, -7, -61, 1, 3, -76, 31, 11, 51, -119, 104, -50, 116, -85, 125, 59, 23, -57, 29, -117, 35, 62, -41, 17, -14, 66}),
			/*com.snapchat.android.app.feature.messaging.chat.fragment.ChatV3Fragment*/ decryptMsg(new byte[]{42, -26, 61, 18, -11, 126, -23, 106, 72, 110, 75, 25, -13, 48, 95, -25, -42, 19, 50, -101, -37, 44, 18, -49, -107, -26, 49, 14, -123, -33, -97, -79, 97, -26, 82, 6, -52, -38, 22, 23, 14, 69, -102, -15, 64, 101, -114, -16, -126, -45, 26, 119, -69, -49, 91, 123, 62, 47, -38, 35, -51, 12, 96, 48, -28, 118, 12, -104, -86, -99, -8, 101, 47, -83, 64, 40, 61, -28, 117, 96})
	);
	public static final HookClass PROFILE_SETTINGS_CREATOR = new HookClass(
			/*PROFILE_SETTINGS_CREATOR*/ decryptMsg(new byte[]{29, 97, 115, -84, 103, -62, -26, 122, -16, -37, -31, 68, -22, -112, -24, 81, 3, -43, 32, 41, -121, -16, 102, 119, 61, 38, 119, -13, 71, -14, 112, 84}),
			/*hdt*/ decryptMsg(new byte[]{21, 56, 19, 36, 79, -78, -49, -8, -119, -28, 0, 37, 9, -73, -122, -49})
	);

	public static class HookClass extends Constant {
		private String strClass;

		public HookClass(String name, @NonNull String value) {
			super(name);
			this.strClass = value;
		}

		public String getStrClass() {
			return strClass;
		}
	}
}
