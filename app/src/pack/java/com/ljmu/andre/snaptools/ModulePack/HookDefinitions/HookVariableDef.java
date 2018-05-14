package com.ljmu.andre.snaptools.ModulePack.HookDefinitions;

import com.ljmu.andre.ConstantDefiner.Constant;
import com.ljmu.andre.ConstantDefiner.ConstantDefiner;
import com.ljmu.andre.snaptools.ModulePack.HookDefinitions.HookVariableDef.HookVariable;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */
public class HookVariableDef extends ConstantDefiner<HookVariable> {
	public static final HookVariable MCANONICALDISPLAYNAME = new HookVariable(
			"MCANONICALDISPLAYNAME",
			"az"

			/** CLASS: {@link HookClassDef.RECEIVED_SNAP} =============================*/
			// CODE CHUNK ================================================================
			/**
			 return (int) (this.az * 1000.0d);
			 ^^^^^^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable STORY_ADVANCER_DISPLAY_STATE = new HookVariable(
			"STORY_ADVANCER_DISPLAY_STATE",
			"f"

			/** CLASS: Parent Of {@link HookClassDef.STORY_ADVANCER} ==================*/
			// CODE CHUNK ================================================================
			/**
			 this.f = bub.NONE;
			 ^^^^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable SNAP_IS_ZIPPED = new HookVariable(
			"SNAP_IS_ZIPPED",
			"ax"

			/** CLASS: {@link HookClassDef.RECEIVED_SNAP} ============================*/
			// CODE CHUNK ================================================================
			/**
			 "isZipped", this.ax
			 ^^^^^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable SENT_MEDIA_TIMESTAMP = new HookVariable(
			"SENT_MEDIA_TIMESTAMP",
			"bt"

			/** CLASS: rbi ==============================================================*/
			// CODE CHUNK ================================================================
			/**
			 *	public long bt = -1;
			 */
	); 

	// ===========================================================================

	public static final HookVariable SENT_MEDIA_BITMAP = new HookVariable(
			"SENT_MEDIA_BITMAP",
			"ay"

			/** CLASS: {@link HookClassDef.SENT_SNAP_BASE} ===========================*/
			// CODE CHUNK ================================================================
			/**
			 *	super.a(this.ay);
			 *           ^^^^^^
			 *	this.ay = null;
			 *	this.J = qjw.RECYCLE;
			 */
	); 

	// ===========================================================================

	public static final HookVariable SENT_MEDIA_VIDEO_URI = new HookVariable(
			"SENT_MEDIA_VIDEO_URI",
			"aG"

			/** CLASS: Parent Of: {@link HookClassDef.SENT_SNAP_BASE} ==================*/
			// CODE CHUNK ================================================================
			/**
			 * public final Uri aG;
			 *
			 * ALSO: return this.aG == null ? null : this.aG.getPath();
			 */
	); 

	public static final HookVariable SENT_BATCHED_VIDEO_MEDIAHOLDER = new HookVariable(
			"SENT_BATCHED_VIDEO_MEDIAHOLDER",
			"c"


			/** CLASS: {@link HookClassDef.SENT_BATCHED_VIDEO} ===========================*/
			// CODE CHUNK ================================================================
			/**
			 * "Original video has no Magikarp playback metadata", this.c.bd
			 *                                                      ^^^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable SENT_MEDIA_BATCH_DATA = new HookVariable(
			"SENT_MEDIA_BATCH_DATA",
			"bP"

			/** CLASS: Interface Of: {@link HookClassDef.SENT_BATCHED_VIDEO} =============*/
			/** FOUND IN: {@link HookClassDef.SENT_VIDEO} ================================*/
			// CODE CHUNK ================================================================
			/**
			 * private rba bG;
			 */
	); 

	// ===========================================================================

	public static final HookVariable CHAT_METADATA_MEDIA = new HookVariable(
			"CHAT_METADATA_MEDIA",
			"c"

			/** CLASS: {@link HookClassDef.CHAT_VIDEO_METADATA} ==========================*/
			// CODE CHUNK ================================================================
			/**
			 if (this.c == null) {
			 ^^^^^^^
			 throw new IllegalStateException("Chat media is null before playing");
			 }
			 */
	); 

	// ===========================================================================

	public static final HookVariable NO_AUTO_ADVANCE = new HookVariable(
			"NO_AUTO_ADVANCE",
			"NO_AUTO_ADVANCE"
	); 

	// ===========================================================================

	public static final HookVariable LENS_CATEGORY = new HookVariable(
			"LENS_CATEGORY",
			"a"
			/** CLASS: {@link HookClassDef.LENS_CATEGORY} ================================*/
			// CODE CHUNK ================================================================
			/**
			 public final String a;
			 */
	); 

	// ===========================================================================

	public static final HookVariable LENS_ACTIVATOR = new HookVariable(
			"LENS_ACTIVATOR",
			"b"

			/** CLASS: {@link HookClassDef.LENS_CATEGORY} ================================*/
			// CODE CHUNK ================================================================
			/**
			 public final ActivatorType b;
			 */
	); 

	// ===========================================================================
	public static final HookVariable SNAPCAPTIONVIEW_CONTEXT = new HookVariable(
			"SNAPCAPTIONVIEW_CONTEXT",
			"b"

			/** CLASS: {@link HookClassDef.CAPTION_MANAGER_CLASS} ========================*/
			// CODE CHUNK ================================================================
			/**
			 private final SnapCaptionView b;
			 */
	); 

	// ===========================================================================

	public static final HookVariable STREAM_TYPE_CHECK_BOOLEAN = new HookVariable(
			"STREAM_TYPE_CHECK_BOOLEAN",
			"d"

			/** CLASS: {@link HookClassDef.ENCRYPTED_STREAM_BUILDER} =====================*/
			// CODE CHUNK ================================================================
			/**
			 (this.d && a.markSupported())
			 ^^^^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable STORY_ADVANCER_METADATA = new HookVariable(
			"STORY_ADVANCER_METADATA",
			"c"

			/** CLASS: Parent Of: {@link HookClassDef.STORY_ADVANCER} ====================*/
			// CODE CHUNK ================================================================
			/**
			 this.d.a("OPEN_VIEW_DISPLAYED", this.c, cez.a("page_view_id", this.p, buv.u, Long.valueOf(tkp.c())));
			 -----------------------------^^^^^^^^^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable CHAT_SAVING_LINKER = new HookVariable(
			"CHAT_SAVING_LINKER",
			"N"

			/** CLASS: {@link HookClassDef.CHAT_MESSAGE_VIEW_HOLDER}======================*/
			// CODE CHUNK ================================================================
			/**
			 * this.N.a(new b[]{this.M, this.p});
			 * ^^^^^^
			 * this.N.a(new b[]{(b) this.L});
			 * if (this.Y != null) {
			 * 	View view2 = this.Y;
			 * 	OnTouchListener mtg = new mtg(this.A);
			 * 	mtg.c = new mje$b(this, (byte) 0);
			 * 	mtg.d = this.R;
			 * 	mtg.b = this.Q;
			 * 	view2.setOnTouchListener(mtg);
			 * }

			 **/
	); 

	// ===========================================================================

	public static final HookVariable CHAT_SAVING_LINKER_CHAT_REF = new HookVariable(
			"CHAT_SAVING_LINKER_CHAT_REF",
			"d"

			// CLASS: lru ================================================================
			// CODE CHUNK ================================================================
			/**
			 public mnt d;
			 ^^^^^^^^^^^^ - The only interfaced non final field
			 public mnl e;
			 public boolean f;
			 */
	); 

	// ===========================================================================

	public static final HookVariable BATCHED_MEDIA_LIST = new HookVariable(
			"BATCHED_MEDIA_LIST",
			"aB"

			/** CLASS: {@link HookClassDef.SENT_VIDEO} ===================================*/
	); 

	// ===========================================================================

	public static final HookVariable BATCHED_MEDIA_ITEM_BOOLEAN = new HookVariable(
			"BATCHED_MEDIA_ITEM_BOOLEAN",
			"b"

			/** CLASS: qkn ===============================================================*/
	); 

	// ===========================================================================

	public static final HookVariable LENS_CATEGORY_MAP = new HookVariable(
			"LENS_CATEGORY_MAP",
			"a"

			/** CLASS: {@link HookClassDef.LENS_CATEGORY_RESOLVER} =======================*/
			// CODE CHUNK ================================================================
			/**
			 * public final Map<String, jkq> a = new HashMap();
			 */
	); 

	// ===========================================================================

	public static final HookVariable GEOFILTER_VIEW_CREATION_ARG3 = new HookVariable(
			"GEOFILTER_VIEW_CREATION_ARG3",
			"a"

			/** CLASS: {@link HookClassDef.FILTER_METADATA_LOADER} =======================*/
			// CODE CHUNK ================================================================
			/**
			 * arrayList.add(ghb.a(a, context, this.a));
			 *                                 ^^^^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable NOTIFICATION_TYPE = new HookVariable(
			"NOTIFICATION_TYPE",
			"a"

			/** CLASS: sof ===============================================================*/
			// CODE CHUNK ================================================================
			/**
			 * this.a = sob.a(bundle.getString("type"));
			 */
	); 

	// ===========================================================================

	public static final HookVariable RECEIVED_SNAP_PAYLOAD_HOLDER = new HookVariable(
			"RECEIVED_SNAP_PAYLOAD_HOLDER",
			"b"

			/** CLASS: {@link HookClassDef.RECEIVED_SNAP_PAYLOAD_BUILDER} ================*/
			// CODE CHUNK ================================================================
			/**
			 * public final rnw getRequestPayload() {
			 *      this.d = new HashMap(this.b.b());
			 *                               ^^^
			 */
	); 

	// ===========================================================================

	public static final HookVariable RECEIVED_SNAP_PAYLOAD_MAP = new HookVariable(
			"RECEIVED_SNAP_PAYLOAD_MAP",
			"a"

			/** CLASS: dyq ===============================================================*/
			// CODE CHUNK ================================================================
			/**
			 * final Map<String, rbl> a;
			 */
	); 

	// ===========================================================================

	public static final HookVariable STORY_UPDATE_METADATA_LIST = new HookVariable(
			"STORY_UPDATE_METADATA_LIST",
			"b"

			/** CLASS: {@link HookClassDef.STORY_SNAP_PAYLOAD_BUILDER} ===================*/
			// CODE CHUNK ================================================================
			/**
			 * private final List<qtg> b;
			 */
	); 

	// ===========================================================================

	public static final HookVariable STORY_UPDATE_METADATA = new HookVariable(
			"STORY_UPDATE_METADATA",
			"a"

			/** CLASS: qjg ===============================================================*/
			// CODE CHUNK ================================================================
			/**
			 * public final xig a = new xig();
			 */
	); 

	// ===========================================================================

	public static final HookVariable STORY_UPDATE_METADATA_ID = new HookVariable(
			"STORY_UPDATE_METADATA_ID",
			"a"

			/** CLASS: xig ===============================================================*/
			// CODE CHUNK ================================================================
			/**
			 * @SerializedName("id")
			 * protected String a;
			 */
	); 

	// ===========================================================================

//	public static final HookVariable SNAPCHAT_FRAGMENT_CONTENT_VIEW = new HookVariable(
//			/*SNAPCHAT_FRAGMENT_CONTENT_VIEW*/ decryptMsg(new byte[]{-87, 117, 119, -29, -76, 52, 120, -51, 117, 22, -12, 11, 74, 120, -21, -122, -7, 11, 111, -74, -25, 26, 80, 87, 75, -34, 88, 8, 12, -89, 31, 54}),
//			"al"
//
//			/** CLASS: SnapchatFragment ==================================================*/
//			// CODE CHUNK ================================================================
//			/**
//			 * public View e_(int i) {
//			 *      return this.al.findViewById(i);
//			 * }
//			 */
//	);

	// ===========================================================================

	public static final HookVariable CHAT_TOP_PANEL_VIEW = new HookVariable(
			"CHAT_TOP_PANEL_VIEW",
			"o"

			/** CLASS: {@link HookClassDef.CHAT_V10_BUILDER}==============================*/
			// CODE CHUNK ================================================================
			/**
			 * this.o = ((ViewStub) this.b.e_(R.id.top_panel_stub)).inflate();
			 */
	); 

	// ===========================================================================

	public static final HookVariable UI_MODE_NAME = new HookVariable(
			"UI_MODE_NAME",
			"d"

			/** CLASS: {@link HookClassDef.CHEETAH_ALLOCATOR}=============================*/
			// CODE CHUNK ================================================================
			/**
			 * private volatile String d = null;
			 */
	); 

	// ===========================================================================

	public static final HookVariable UI_MODE_ENUM = new HookVariable(
			"UI_MODE_ENUM",
			"b"

			/** CLASS: {@link HookClassDef.CHEETAH_ALLOCATOR}=============================*/
			// CODE CHUNK ================================================================
			/**
			 * private volatile dzk$a$a b = dzk$a$a.OLD_DESIGN;
			 */
	); 

	// ===========================================================================

	public static final HookVariable STORY_COLLECTION_MAP = new HookVariable(
			"STORY_COLLECTION_MAP",
			"d"

			/** CLASS: {@link HookClassDef.STORY_MANAGER}=================================*/
			// CODE CHUNK ================================================================
			/**
			 * protected final Map<String, qsj> d = new ConcurrentHashMap();
			 * qsj -> Search for "StoryCollection"
			 */
	); 

	// ===========================================================================

	public static final HookVariable FILTER_METADATA_CACHE = new HookVariable(
			"FILTER_METADATA_CACHE",
			"a"

			/** CLASS: {@link HookClassDef.FILTER_METADATA_CREATOR}=======================*/
			// CODE CHUNK ================================================================
			/**
			 * private /* synthetic / qvs a;
			 */
	); 

	// ===========================================================================

	public static final HookVariable FILTER_SERIALIZABLE_METADATA = new HookVariable(
			"FILTER_SERIALIZABLE_METADATA",
			"a"

			/** CLASS: qvs ===============================================================*/
			// CODE CHUNK ================================================================
			/**
			 * qvs -> Search for "EncryptedGeoLoggingData is null: server returned empty response for"
			 *
			 * public final wsa a;
			 * wsa -> {@link HookClassDef.SERIALIZABLE_FILTER_METADATA}
			 */
	); 

	// ===========================================================================


	/**
	 * ===========================================================================
	 * UNDEFINED HOOK METHODS
	 * ===========================================================================
	 * These "variables" are just names of hooks for classes that we do not care
	 * about the name of
	 * ===========================================================================
	 */
	public static final HookVariable STORY_DATA_IS_SUBSCRIBED = new HookVariable(
			"STORY_DATA_IS_SUBSCRIBED",
			"s"

			/** CLASS: {@link HookClassDef.STORY_MANAGER}=================================*/
			// CODE CHUNK ================================================================
			/**
			 * Find function that returns variable: ", isSubscribed=" + this.s
			 */
	); 
	// ===========================================================================


	public static class HookVariable extends Constant {
		private final String varName;

		HookVariable(String name, String varName) {
			super(name);
			this.varName = varName;
		}

		public String getVarName() {
			return this.varName;
		}
	}
}
