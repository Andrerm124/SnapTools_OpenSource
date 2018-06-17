package com.ljmu.andre.snaptools.ModulePack.Fragments.Children;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljmu.andre.CBIDatabase.CBITable;
import com.ljmu.andre.CBIDatabase.Utils.QueryBuilder;
import com.ljmu.andre.snaptools.Dialogs.Content.Options.OptionsButtonData;
import com.ljmu.andre.snaptools.Dialogs.Content.TextInputBasic;
import com.ljmu.andre.snaptools.Dialogs.DialogFactory;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog;
import com.ljmu.andre.snaptools.Dialogs.ThemedDialog.ThemedClickListener;
import com.ljmu.andre.snaptools.EventBus.EventBus;
import com.ljmu.andre.snaptools.EventBus.Events.ReqLoadChatFragmentEvent;
import com.ljmu.andre.snaptools.Fragments.FragmentHelper;
import com.ljmu.andre.snaptools.ModulePack.Databases.ChatDatabase;
import com.ljmu.andre.snaptools.ModulePack.Databases.Tables.ConversationObject;
import com.ljmu.andre.snaptools.ModulePack.Utils.ConversationUtils;
import com.ljmu.andre.snaptools.R;
import com.ljmu.andre.snaptools.Utils.AnimationUtils;
import com.ljmu.andre.snaptools.Utils.Constants;
import com.ljmu.andre.snaptools.Utils.CustomObservers.SimpleObserver;
import com.ljmu.andre.snaptools.Utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.ljmu.andre.snaptools.Utils.ResourceUtils.getLayout;
import static com.ljmu.andre.snaptools.Utils.StringEncryptor.decryptMsg;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class ChatConversationFragment extends FragmentHelper {
	private Activity activity;
	private CBITable<ConversationObject> conversationTable;
	private SwipeRefreshLayout swipeRefreshLayout;
	private RecyclerView recyclerView;
	private ConversationQuickAdapter adapter;
	private List<ConversationObject> conversations = new ArrayList<>();

	@Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		activity = getActivity();
		EventBus.soleRegister(this);
		ChatDatabase.init(getActivity());
		conversationTable = ChatDatabase.getTable(ConversationObject.class);

		LinearLayout layoutContainer = new LinearLayout(getContext());
		layoutContainer.setOrientation(LinearLayout.VERTICAL);
		layoutContainer.setLayoutParams(
				new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		);

		initUsernameList(layoutContainer, inflater);
		return layoutContainer;
	}

	@Override public void onResume() {
		super.onResume();
		generateConversationData();
	}

	@Override public void onPause() {
		super.onPause();
		conversations.clear();
	}

	private void initUsernameList(LinearLayout layoutContainer, LayoutInflater inflater) {
		swipeRefreshLayout = new SwipeRefreshLayout(getActivity());
		swipeRefreshLayout.setLayoutParams(
				new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
		);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override public void onRefresh() {
				generateConversationData();
			}
		});

		inflater.inflate(getLayout(getActivity(), "recyclerview"), swipeRefreshLayout, true);

		recyclerView = ResourceUtils.getView(swipeRefreshLayout, "recyclerview");
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		adapter = new ConversationQuickAdapter(conversations);
		adapter.bindToRecyclerView(recyclerView);
		adapter.setEmptyView(R.layout.layout_empty_chats);

		adapter.setOnItemClickListener(new OnItemClickListener() {
			@Override public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Timber.d("Clicked item: " + position);
			}
		});
		layoutContainer.addView(swipeRefreshLayout);
	}

	private void generateConversationData() {
		swipeRefreshLayout.setRefreshing(true);
		Observable.fromCallable(new Callable<Collection<ConversationObject>>() {
			@Override public Collection<ConversationObject> call() throws Exception {
				Collection<ConversationObject> conversationObjects = conversationTable.getAll(
						new QueryBuilder()
								.addSort("last_message_timestamp", "DESC")
				);

				if (conversationObjects.isEmpty())
					return Collections.emptyList();

				return conversationObjects;
			}
		}).subscribeOn(Schedulers.computation())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new SimpleObserver<Collection<ConversationObject>>() {
					@Override public void onNext(@NonNull Collection<ConversationObject> conversationItems) {
						setConversations(conversationItems);
						swipeRefreshLayout.setRefreshing(false);
					}

					@Override public void onError(@NonNull Throwable e) {
						super.onError(e);
						swipeRefreshLayout.setRefreshing(false);
					}
				});
	}

	public void setConversations(Collection<ConversationObject> conversations) {
		recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				//At this point the layout is complete and the
				//dimensions of recyclerView and any child views are known.

				if(Constants.getApkVersionCode() >= 66)
					AnimationUtils.sequentGroup(recyclerView);

				recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
		});

		this.conversations.clear();
		this.conversations.addAll(conversations);
		//Collections.sort(chatMessages);

		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	private LinearLayout createUsernameRow(ConversationObject conversationObject) {
		LinearLayout container = new LinearLayout(getActivity());
		container.setOrientation(LinearLayout.VERTICAL);
		container.setLayoutParams(
				new LinearLayoutCompat.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		);

		container.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				EventBus.getInstance().post(
						new ReqLoadChatFragmentEvent(
								new ChatMessagesFragment().setConversationObject(conversationObject)
						)
				);
			}
		});
		TextView username = new TextView(getActivity());
		username.setText(conversationObject.getConversationName());
		username.setTextAppearance(activity, R.style.HeaderText);

		container.addView(username);

		TextView timestamp = new TextView(getActivity());
		timestamp.setText(
				DateUtils.getRelativeDateTimeString(
						getActivity(),
						conversationObject.timestamp,
						DateUtils.MINUTE_IN_MILLIS,
						DateUtils.WEEK_IN_MILLIS,
						DateUtils.FORMAT_ABBREV_RELATIVE
				)
		);

		container.addView(timestamp);
		return container;
	}

	@Override public String getName() {
		return null;
	}

	@Override public Integer getMenuId() {
		return null;
	}

	@Override public void onDestroy() {
		super.onDestroy();
		EventBus.soleUnregister(this);
	}

	private void renameConversation(ConversationObject conversationObject) {
		DialogFactory.createBasicTextInputDialog(
				getActivity(),
				/*Rename Conversation*/ decryptMsg(new byte[]{68, 88, 81, -53, -53, 113, 46, 47, 83, 97, -113, -17, -34, 77, 1, -10, 48, 93, 118, -56, -16, 52, -6, 100, -68, -103, -72, 41, -82, 0, -84, 83}),
				/*Please enter a name for the conversation*/ decryptMsg(new byte[]{-104, -114, -102, -87, 109, -85, -28, -75, 124, 42, -94, -23, 114, -22, -128, -106, -32, -121, 67, 72, 123, -92, -122, 78, 111, 36, -55, 92, -11, 40, 125, 12, 57, -121, 63, 57, -87, 124, 75, -75, 16, 11, 56, -53, 22, 53, 39, 53}),
				null,
				conversationObject.getConversationName(),
				null,
				new ThemedClickListener() {
					@Override public void clicked(ThemedDialog themedDialog) {
						TextInputBasic inputExtension = themedDialog.getExtension();
						conversationObject.conversationName = inputExtension.getInputMessage();
						conversationTable.insert(conversationObject);
						themedDialog.dismiss();
						generateConversationData();
					}
				}
		).show();
	}

	private void exportConversation(ConversationObject conversationObject) {
		DialogFactory.createOptions(
				getActivity(),
				"Export Conversation",
				new OptionsButtonData(
						"Export as JSON",
						new ThemedClickListener() {
							@Override public void clicked(ThemedDialog themedDialog) {
								String defaultFilename = conversationObject.getConversationName();
								ConversationUtils.requestFilename(
										getActivity(),
										defaultFilename,
										s -> ConversationUtils.exportConversationAsJson(
												getActivity(),
												s,
												conversationObject
										)
								);

								themedDialog.dismiss();
							}
						}
				),
				new OptionsButtonData(
						"Export as TXT",
						new ThemedClickListener() {
							@Override public void clicked(ThemedDialog themedDialog) {
								String defaultFilename = conversationObject.getConversationName();
								ConversationUtils.requestFilename(
										getActivity(),
										defaultFilename,
										s -> ConversationUtils.exportConversationAsTxt(
												getActivity(),
												s,
												conversationObject
										)
								);

								themedDialog.dismiss();
							}
						}
				)
		).show();
	}

	private void deleteConversation(ConversationObject conversationObject) {
		DialogFactory.createConfirmation(
				getActivity(),
				/*Delete Confirmation?*/ decryptMsg(new byte[]{-15, -56, 40, -74, -84, -70, 84, 49, 115, 109, -94, 91, -25, 122, -121, 27, 48, -80, -36, 99, -95, 69, -15, -41, -74, 16, 59, -107, 85, -58, 69, 73}),
				/*Are you sure you want to delete this conversation (*/ decryptMsg(new byte[]{124, -63, 53, -11, 86, -30, -46, 37, -98, -41, 23, -17, -27, 80, 93, -10, -83, 49, -22, -39, 44, 11, -48, 15, 71, 55, -29, -28, 84, -33, 102, 88, 43, 30, -92, -45, -26, 88, 83, -86, 55, 8, 15, 52, 24, -97, -14, 73, -79, -113, 107, 19, -85, -54, -20, -69, 1, -21, 50, -73, 7, -64, -103, -42})
						+ conversationObject.getConversationName() + ")?",
				new ThemedClickListener() {
					@Override public void clicked(ThemedDialog themedDialog) {
						conversationTable.delete(conversationObject);
						themedDialog.dismiss();
						generateConversationData();
					}
				}
		).show();
	}

	public class ConversationQuickAdapter extends BaseMultiItemQuickAdapter<ConversationObject, BaseViewHolder> {
		/**
		 * Same as QuickAdapter#QuickAdapter(Context,int) but with
		 * some initialization data.
		 *
		 * @param data A new list is created out of this one to avoid mutable list
		 */
		public ConversationQuickAdapter(List<ConversationObject> data) {
			super(data);
			addItemType(ConversationObject.TYPE, getLayout(getActivity(), "item_conversation"));
		}

		@Override protected void convert(BaseViewHolder helper, ConversationObject item) {
			item.setupDisplayView(helper);

			helper.itemView.setOnClickListener(
					v -> EventBus.getInstance().post(
							new ReqLoadChatFragmentEvent(
									new ChatMessagesFragment().setConversationObject(item)
							)
					));

			helper.itemView.setOnLongClickListener(v -> {
				DialogFactory.createOptions(
						getActivity(),
						"Conversation",
						new OptionsButtonData(
								"Delete Conversation",
								new ThemedClickListener() {
									@Override public void clicked(ThemedDialog themedDialog) {
										deleteConversation(item);
										themedDialog.dismiss();
									}
								}
						),
						new OptionsButtonData(
								"Rename Conversation",
								new ThemedClickListener() {
									@Override public void clicked(ThemedDialog themedDialog) {
										renameConversation(item);
										themedDialog.dismiss();
									}
								}
						),
						new OptionsButtonData(
								"Export Conversation",
								new ThemedClickListener() {
									@Override public void clicked(ThemedDialog themedDialog) {
										exportConversation(item);
										themedDialog.dismiss();
									}
								}
						)
				).show();
				return false;
			});
		}
	}
}
