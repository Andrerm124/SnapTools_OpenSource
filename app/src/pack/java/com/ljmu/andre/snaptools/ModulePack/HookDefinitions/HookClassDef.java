package com.ljmu.andre.snaptools.ModulePack.HookDefinitions;

import android.support.annotation.NonNull;

import com.ljmu.andre.ConstantDefiner.Constant;
import com.ljmu.andre.ConstantDefiner.ConstantDefiner;
import com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookClassDef.HookClass;

/**
 * ===========================================================================
 * Hook Generation | Contains: Classes, Methods/Constructors, Variables
 * ===========================================================================
 */
@SuppressWarnings({"WeakerAccess"})
public class HookClassDef extends ConstantDefiner<HookClass> {
	public static final HookClassDef INST = new HookClassDef();

	public static final HookClass SCREENSHOT_DETECTOR = new HookClass(
			"SCREENSHOT_DETECTOR",
			"ssr"
	);// qtz 
	//	public static final HookClass SNAP_DATA_JSON = new HookClass(
//			/*SNAP_DATA_JSON*/ decryptMsg(new byte[]{-93, 21, 88, -115, -63, 105, -59, -35, -5, 104, -29, 64, 117, -100, 76, 87}),
//			/*umf*/ decryptMsg(new byte[]{25, -90, 33, -88, -30, 1, 58, -113, 73, -119, -15, 78, 51, 30, -51, -51})
//	);
	public static final HookClass TEXTURE_VIDEO_VIEW = new HookClass(
			"TEXTURE_VIDEO_VIEW",
			"com.snap.opera.shared.view.TextureVideoView"
	); // com.snapchat.opera.shared.view.TextureVideoView 
	public static final HookClass SNAP_COUNTDOWN_CONTROLLER = new HookClass(
			"SNAP_COUNTDOWN_CONTROLLER",
			"qiu"
	);// okm 
	public static final HookClass ENCRYPTION_ALGORITHM = new HookClass(
			"ENCRYPTION_ALGORITHM",
			"com.snapchat.android.framework.crypto.CbcEncryptionAlgorithm"
	); // com.snapchat.android.framework.crypto.CbcEncryptionAlgorithm 
	public static final HookClass ENCRYPTED_STREAM_BUILDER = new HookClass(
			"ENCRYPTED_STREAM_BUILDER",
			"sfo"
	); // qgr 
	public static final HookClass STORY_METADATA = new HookClass(
			"STORY_METADATA",
			"cez"
	); // tul 
	public static final HookClass SNAP_BASE = new HookClass(
			"SNAP_BASE",
			"mph"
	); // kxs 
	public static final HookClass SNAP_STATUS = new HookClass(
			"SNAP_STATUS",
			"mph$a"
	); // kxs$a 
	public static final HookClass RECEIVED_SNAP = new HookClass(
			"RECEIVED_SNAP",
			"rbl"
	); // pcb 
	public static final HookClass RECEIVED_SNAP_ENCRYPTION = new HookClass(
			"RECEIVED_SNAP_ENCRYPTION",
			"mqt"
	); // kyr 
	public static final HookClass RECEIVED_SNAP_PAYLOAD_BUILDER = new HookClass(
			"RECEIVED_SNAP_PAYLOAD_BUILDER",
			"dys"
	); // dfy 
	public static final HookClass STORY_SNAP_PAYLOAD_BUILDER = new HookClass(
			"STORY_SNAP_PAYLOAD_BUILDER",
			"ejj"
	); // dnj 
	public static final HookClass GROUP_SNAP_METADATA = new HookClass(
			"GROUP_SNAP_METADATA",
			"mai"
	); // kiz  - Changed a bit
	public static final HookClass STORY_SNAP = new HookClass(
			"STORY_SNAP",
			"qta"
	); // oud 
	public static final HookClass STORY_STATUS_UPDATER = new HookClass(
			"STORY_STATUS_UPDATER",
			"ejw"
	); // dnu 
	public static final HookClass STORY_METADATA_LOADER = new HookClass(
			"STORY_METADATA_LOADER",
			"eur"
	); // dye 
	public static final HookClass STORY_ADVANCER = new HookClass(
			"STORY_ADVANCER",
			"buf"
	); // tka 
	public static final HookClass SENT_IMAGE = new HookClass(
			"SENT_IMAGE",
			"rbp"
	); // pcf 
	public static final HookClass SENT_VIDEO = new HookClass(
			"SENT_VIDEO",
			"rcf"
	); // pct 
	public static final HookClass SENT_BATCHED_VIDEO = new HookClass(
			"SENT_BATCHED_VIDEO",
			"fql"
	); // eoo 
	public static final HookClass ENUM_BATCHED_SNAP_POSITION = new HookClass(
			"ENUM_BATCHED_SNAP_POSITION",
			"ddf"
	); // cjh 
	public static final HookClass SENT_SNAP_BASE = new HookClass(
			"SENT_SNAP_BASE",
			"rat"
	); // pbk 
	public static final HookClass META_DATA_BUILDER = new HookClass(
			"META_DATA_BUILDER",
			"ncg"
	); // ljn 
	public static final HookClass CHAT_IMAGE_METADATA = new HookClass(
			"CHAT_IMAGE_METADATA",
			"lzv"
	); // kin 
	public static final HookClass CHAT_VIDEO_METADATA = new HookClass(
			"CHAT_VIDEO_METADATA",
			"msp"
	); // lam 
	public static final HookClass CHAT_VIDEO = new HookClass(
			"CHAT_VIDEO",
			"mmc"
	); // kun 
	public static final HookClass NEW_CONCENTRIC_TIMERVIEW = new HookClass(
			"NEW_CONCENTRIC_TIMERVIEW",
			"com.snapchat.android.framework.ui.views.NewConcentricTimerView"
	); // com.snapchat.android.framework.ui.views.NewConcentricTimerView 
	public static final HookClass COUNTDOWNTIMER_VIEW = new HookClass(
			"COUNTDOWNTIMER_VIEW",
			"com.snapchat.android.framework.ui.views.CountdownTimerView"
	); // com.snapchat.android.framework.ui.views.CountdownTimerView* 
	public static final HookClass LENS_AUTHENTICATION = new HookClass(
			"LENS_AUTHENTICATION",
			"jij"
	); // hvm 
	public static final HookClass ENUM_SNAP_ADVANCE_MODES = new HookClass(
			"ENUM_SNAP_ADVANCE_MODES",
			"cad"
	); // tpt 
	public static final HookClass LENS = new HookClass(
			"LENS",
			"jkk"
	); // hxb 
	public static final HookClass LENS_CATEGORY_RESOLVER = new HookClass(
			"LENS_CATEGORY_RESOLVER",
			"jkr"
	); // hxh 
	public static final HookClass LENS_LOADER = new HookClass(
			"LENS_LOADER",
			"jhg"
	); // hum 
	public static final HookClass LENS_CONTEXT_HOLDER = new HookClass(
			"LENS_CONTEXT_HOLDER",
			"jkv"
	); // hum 
	public static final HookClass LENS_CAMERA_CONTEXT_ENUM = new HookClass(
			"LENS_CAMERA_CONTEXT_ENUM",
			"jko"
	); // hum 
	public static final HookClass LENS_APPLICATION_CONTEXT_ENUM = new HookClass(
			"LENS_APPLICATION_CONTEXT_ENUM",
			"jkl"
	); // hum 
	public static final HookClass STORY_LOADER = new HookClass(
			"STORY_LOADER",
			"ees"
	); // dir 
	public static final HookClass STORY_SPONSORED = new HookClass(
			"STORY_ADVERT",
			"esr"
	); // dwm 
	public static final HookClass STORY_FRIEND_RECENT = new HookClass(
			"STORY_FRIEND_RECENT",
			"erc"
	); // dux 
	public static final HookClass STORY_FRIEND_VIEWED = new HookClass(
			"STORY_FRIEND_VIEWED",
			"eqj"
	); // dud 
	public static final HookClass STORY_SNAP_AD_LOADER = new HookClass(
			"STORY_SNAP_AD_LOADER",
			"ehu"
	); // dlv 
	public static final HookClass FRIEND_PROFILE_POPUP_FRAGMENT = new HookClass(
			"FRIEND_PROFILE_POPUP_FRAGMENT",
			"com.snapchat.android.app.feature.miniprofile.internal.friend.FriendMiniProfilePopupFragment"
	); // com.snapchat.android.app.feature.miniprofile.internal.friend.FriendMiniProfilePopupFragment 
	public static final HookClass USER_PREFS = new HookClass(
			"USER_PREFS",
			"sww"
	); // com.snapchat.android.core.user.UserPrefs 
	public static final HookClass CAMERA_FRAGMENT = new HookClass(
			"CAMERA_FRAGMENT",
			"com.snapchat.android.app.main.camera.CameraFragmentV1"
	); // com.snapchat.android.app.main.camera.CameraFragmentV1 
	public static final HookClass ENUM_LENS_TYPE = new HookClass(
			"ENUM_LENS_TYPE",
			"jkk$b"
	); // hxb$b 
	public static final HookClass LENS_CATEGORY = new HookClass(
			"LENS_CATEGORY",
			"jkt"
	); // hxj 
	public static final HookClass LENS_SLUG = new HookClass(
			"LENS_SLUG",
			"xet"
	); // vdi 
	public static final HookClass LENS_TRACK = new HookClass(
			"LENS_TRACK",
			"xjk"
	); // vha 
	//	public static final HookClass LENS_ASSET = new HookClass(
//			/*LENS_ASSET*/ decryptMsg(new byte[]{103, -30, 45, 86, 2, -11, -90, -118, -30, -38, 18, -82, -100, 100, 81, -9}),
//			/*qvy*/ decryptMsg(new byte[]{59, -101, -61, 47, 114, 126, 100, -69, -33, 38, -46, 109, -46, 5, -107, 12})
//	);
	public static final HookClass LENS_ASSET_BUILT = new HookClass(
			"LENS_ASSET_BUILT",
			"jkm"
	); // hxd 
	public static final HookClass LENS_ASSET_TYPE = new HookClass(
			"LENS_ASSET_TYPE",
			"jkm$a"
	); // hxd$a 
	public static final HookClass LENS_ASSET_LOAD_MODE = new HookClass(
			"LENS_ASSET_LOAD_MODE",
			"jlb"
	); // hxq 
	public static final HookClass ENUM_LENS_ACTIVATOR_TYPE = new HookClass(
			"ENUM_LENS_ACTIVATOR_TYPE",
			"com.looksery.sdk.domain.Category.ActivatorType"
	); // com.looksery.sdk.domain.Category.ActivatorType 
	public static final HookClass FONT_CLASS = new HookClass(
			"FONT_CLASS",
			"android.graphics.Typeface"
	); // android.graphics.Typeface 
	public static final HookClass SNAPCHAT_CAPTION_VIEW_CLASS = new HookClass(
			"SNAPCHAT_CAPTION_VIEW_CLASS",
			"com.snapchat.android.app.feature.creativetools.caption.SnapCaptionView"
	); // com.snapchat.android.app.feature.creativetools.caption.SnapCaptionView 
	public static final HookClass CAPTION_MANAGER_CLASS = new HookClass(
			"CAPTION_MANAGER_CLASS",
			"gau"
	); // eyr 
	public static final HookClass CHAT_MESSAGE_VIEW_HOLDER = new HookClass(
			"CHAT_MESSAGE_VIEW_HOLDER",
			"mje"
	); // krr 
	public static final HookClass OPERA_PAGE_VIEW = new HookClass(
			"OPERA_PAGE_VIEW",
			"com.snap.opera.view.OperaPageView"
	); // com.snapchat.opera.view.OperaPageView 
	public static final HookClass CHAT_METADATA_JSON_PARSER = new HookClass(
			"CHAT_METADATA_JSON_PARSER",
			"wjo"
	); // ulu 
	public static final HookClass CHAT_METADATA_JSON_PARSER_SECOND = new HookClass(
			"CHAT_METADATA_JSON_PARSER_SECOND",
			"wxx"
	); // uxi 
	public static final HookClass CHAT_METADATA = new HookClass(
			"CHAT_METADATA",
			"wjn"
	); // ult 
	public static final HookClass CHAT_HEADER_METADATA = new HookClass(
			"CHAT_HEADER_METADATA",
			"wso"
	); // usz 
	public static final HookClass CHAT_BODY_METADATA = new HookClass(
			"CHAT_BODY_METADATA",
			"wxa"
	); // uwu 
	public static final HookClass GEOFILTER_VIEW = new HookClass(
			"GEOFILTER_VIEW",
			"com.snapchat.android.app.shared.feature.preview.ui.smartfilters.GeofilterView"
	); // com.snapchat.android.app.shared.feature.preview.ui.smartfilters.GeofilterView 
	public static final HookClass IMAGE_GEOFILTER_VIEW = new HookClass(
			"IMAGE_GEOFILTER_VIEW",
			"com.snapchat.android.app.shared.feature.preview.ui.smartfilters.ImageGeofilterView"
	); // com.snapchat.android.app.shared.feature.preview.ui.smartfilters.ImageGeofilterView 
	public static final HookClass FILTER_METADATA = new HookClass(
			"FILTER_METADATA",
			"quj"
	); // ovk 
	public static final HookClass SERIALIZABLE_FILTER_METADATA = new HookClass(
			"SERIALIZABLE_FILTER_METADATA",
			"wsa"
	); // usn 
	public static final HookClass FILTER_METADATA_LOADER = new HookClass(
			"FILTER_METADATA_LOADER",
			"ghu"
	); // fek 
	public static final HookClass FILTER_METADATA_CREATOR = new HookClass(
			"FILTER_METADATA_CREATOR",
			"qvs$2"
	); // owp$2 
	public static final HookClass GEOFILTER_VIEW_CREATOR = new HookClass(
			"GEOFILTER_VIEW_CREATOR",
			"ghb"
	); // fdr 
	public static final HookClass CHAT_NOTIFICATION_CREATOR = new HookClass(
			"CHAT_NOTIFICATION_CREATOR",
			"lmq"
	); // jvy 
	public static final HookClass NETWORK_MANAGER = new HookClass(
			"NETWORK_MANAGER",
			"sjv"
	); // qlc 
	public static final HookClass NETWORK_DISPATCHER = new HookClass(
			"NETWORK_DISPATCHER",
			"lpo"
	); // jyp 
	//	public static final HookClass CHAT_MESSAGE_METADATA = new HookClass(
//			/*CHAT_MESSAGE_METADATA*/ decryptMsg(new byte[]{-50, 44, 120, -82, 113, -65, 81, -120, 5, -126, -76, -93, 71, -33, -116, -17, 35, 108, 78, -36, 77, -82, 21, 99, -117, 72, 87, 32, 49, 74, 108, -30}),
//			/*ktw*/ decryptMsg(new byte[]{123, 84, -31, -44, -43, 3, -88, 68, 30, 56, -104, 107, 47, -128, 23, 71})
//	);
	public static final HookClass CHAT_GROUP_VIEW_MARKER = new HookClass(
			"CHAT_GROUP_VIEW_MARKER",
			"lrk"
	); // kal 
	public static final HookClass CHAT_DIRECT_VIEW_MARKER = new HookClass(
			"CHAT_DIRECT_VIEW_MARKER",
			"lqg"
	); // jzh 
	public static final HookClass CHAT_V3_FRAGMENT = new HookClass(
			"CHAT_V3_FRAGMENT",
			"com.snapchat.android.app.feature.messaging.chat.fragment.ChatV3Fragment"
	); // com.snapchat.android.app.feature.messaging.chat.fragment.ChatV3Fragment
	public static final HookClass CHAT_V10_BUILDER = new HookClass(
			"CHAT_V10_BUILDER",
			"luf"
	); // kdd 
	public static final HookClass PROFILE_SETTINGS_CREATOR = new HookClass(
			"PROFILE_SETTINGS_CREATOR",
			"ipn"
	); // hem 
	public static final HookClass CHEETAH_PROFILE_SETTINGS_CREATOR = new HookClass(
			"CHEETAH_PROFILE_SETTINGS_CREATOR",
			"irw"
	); // hgr 
	public static final HookClass AB_TEST_MANAGER = new HookClass(
			"AB_TEST_MANAGER",
			"btd"
	); // rak 
	public static final HookClass CHEETAH_EXPERIMENT_ENUM = new HookClass(
			"CHEETAH_EXPERIMENT_ENUM",
			"dzk$a$a"
	); // dgq$a$a 
	public static final HookClass CHEETAH_ALLOCATOR = new HookClass(
			"CHEETAH_ALLOCATOR",
			"dzl"
	); // dgr 
	public static final HookClass EXPERIMENT_BASE = new HookClass(
			"EXPERIMENT_BASE",
			"bsz"
	); // rbr 
	public static final HookClass CHEETAH_EXPERIMENT = new HookClass(
			"CHEETAH_EXPERIMENT",
			"dzk$1$1"
	); // dgq$1$1 
	public static final HookClass STORY_MANAGER = new HookClass(
			"STORY_MANAGER",
			"eoa"
	); // dru 
	public static final HookClass STORY_DATA_DISCOVER = new HookClass(
			"STORY_DATA_DISCOVER",
			"aazh"
	); // yph 
	public static final HookClass STORY_DATA_DYNAMIC = new HookClass(
			"STORY_DATA_DYNAMIC",
			"aazi"
	); // ypi 
	public static final HookClass STORY_DATA_MAP = new HookClass(
			"STORY_DATA_MAP",
			"aazj"
	); // ypj 
	public static final HookClass STORY_DATA_PROMOTED = new HookClass(
			"STORY_DATA_PROMOTED",
			"aazk"
	); // ypk 
	public static final HookClass STORY_DATA_MOMENT = new HookClass(
			"STORY_DATA_MOMENT",
			"aazu"
	); // ypt 
	public static final HookClass DOWNLOADER_RUNNABLE = new HookClass(
			"DOWNLOADER_RUNNABLE",
			"smd$2"
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
