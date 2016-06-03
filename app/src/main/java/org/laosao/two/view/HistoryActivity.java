package org.laosao.two.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.laosao.two.R;
import org.laosao.two.bean.History;
import org.laosao.two.model.ImageUtils;
import org.laosao.two.model.OtherUtils;
import org.laosao.two.model.SDCard;
import org.laosao.two.present.HistoryPresent;
import org.laosao.two.view.base.BaseActivity;
import org.laosao.two.view.iview.IHistoryView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import material.dialog.MaterialDialog;
import material.view.fab.FloatingActionButton;

/**
 * @author 赵树豪
 * @version 1.0
 */
public class HistoryActivity extends BaseActivity<HistoryPresent>
        implements IHistoryView {
    private RecyclerView mRvContent;
    private List<History> mHistories;
    private MyAdapter mAdapter;

    @Override
    public HistoryPresent createPersent() {
        return new HistoryPresent(this, this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_history;
    }

    @Override
    public void initView() {
        mRvContent = (RecyclerView) findViewById(R.id.rvContent);
    }

    @Override
    public void loadData() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                File file = SDCard.rootDir;
                if (file.exists()) {
                    final File[] files = file.listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".png")) {
                                return true;
                            }
                            return false;
                        }
                    });
                    mHistories = new ArrayList<>();
                    for (File f : files) {
                        if (!f.isDirectory()) {
                            String filePath = f.getAbsolutePath();
                            String fileName = f.getName().replace(".png", "");
                            Bitmap b = ImageUtils.matrixXYBitmap(filePath, 75, 75);
                            History h = new History(b, fileName, filePath);
                            mHistories.add(h);
                        }
                    }
                    return mHistories.size() == 0 ? false : true;
                } else {
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean b) {
                if (b) {
                    mAdapter = new MyAdapter(HistoryActivity.this, mHistories);
                    mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String path = mHistories.get(position).getFilePath();
                            File f = new File(path);
                            OtherUtils.showBitmap(HistoryActivity.this, f);
                        }
                    });
                    mAdapter.setOnDeleteListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position) {
                            final MaterialDialog dialog = new MaterialDialog(HistoryActivity.this);
                            dialog.setTitle("删除该文件");
                            dialog.setMessage("是否确认删除该文件？");
                            dialog.setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String path = mHistories.get(position).getFilePath();
                                    File f = new File(path);
                                    f.delete();
                                    mAdapter.updateData(position);
                                    dialog.dismiss();
                                }
                            });
                            dialog.setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    });
                    mAdapter.setOnShareListener(new MyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String path = mHistories.get(position).getFilePath();
                            File f = new File(path);
                            OtherUtils.share(HistoryActivity.this, f);
                        }
                    });
                    mRvContent.setAdapter(mAdapter);
                } else {
                    showToast("暂无保存记录", Toast.LENGTH_LONG);
                }
                mDialog.dismiss();
            }
        }.execute(null, null);
    }

    @Override
    public void setListener() {

    }

    @Override
    public String getContent() {
        return null;
    }

    private MaterialDialog mDialog;

    @Override
    public void showWaitDialog() {
        mDialog = OtherUtils.showWaitDialog(this);
    }

    @Override
    public void tipNoPermission() {
        showToast("未获得存储卡读写权限，无法读取，请授权", Toast.LENGTH_LONG);
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        LayoutInflater mInflater;
        List<History> mHistories;

        public MyAdapter(Context context, List data) {
            mInflater = LayoutInflater.from(context);
            mHistories = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.item_history, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.mTvName.setText(mHistories.get(position).getFileName());
            holder.mIvQrcode.setImageBitmap(mHistories.get(position).getBmpQrcode());
//            列表项单击事件
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
            //            删除按钮事件
            if (mOnDeleteListener != null) {
                holder.mFabDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnDeleteListener.onItemClick(holder.mFabDelete, position);
                    }
                });
            }
            //            分享按钮事件
            if (mOnShareListener != null) {
                holder.mFabShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnShareListener.onItemClick(holder.mFabShare, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mHistories.size();
        }

        public void updateData(int position) {
            mHistories.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }

        OnItemClickListener mOnShareListener;
        OnItemClickListener mOnDeleteListener;
        OnItemClickListener mOnItemClickListener;

        public void setOnShareListener(OnItemClickListener onShareListener) {
            mOnShareListener = onShareListener;
        }

        public void setOnDeleteListener(OnItemClickListener onDeleteListener) {
            mOnDeleteListener = onDeleteListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView mIvQrcode;
            TextView mTvName;
            FloatingActionButton mFabShare;
            FloatingActionButton mFabDelete;

            public MyViewHolder(View itemView) {
                super(itemView);
                mIvQrcode = (ImageView) itemView.findViewById(R.id.ivQrcode);
                mTvName = (TextView) itemView.findViewById(R.id.tvQrcodeName);
                mFabShare = (FloatingActionButton) itemView.findViewById(R.id.fabShare);
                mFabDelete = (FloatingActionButton) itemView.findViewById(R.id.fabDelete);
            }
        }
    }
}
