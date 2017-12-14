package com.gzlygl.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.BaseRecyclerAdapter;
import com.gzlygl.R;
import com.gzlygl.javabeen.Comment;
import com.gzlygl.javabeen.Diary;
import com.gzlygl.javabeen.User;
import com.gzlygl.util.PreferencesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SQLQueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DengJf on 2017/12/5.
 */

public class CommentBaseRecyclerViewAdapter  extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder,Comment> {
    private Context context;
    int resouse;
    private List<Comment>data;
    private List<Msg>msgs;
    private PreferencesUtil preferencesUtil;




    public CommentBaseRecyclerViewAdapter(Context context,List<Comment> mDataList,int resouse) {
        super(mDataList);
        this.context=context;
        this.resouse=resouse;
        data=mDataList;
        preferencesUtil=new PreferencesUtil(context);
        msgs=new ArrayList<>();
        for (int i=0;i<mDataList.size();i++){
            Msg msg=new Msg();
            msg.setComment(data.get(i));
            msgs.add(msg);
        }
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = (ViewHolder) createViewHolder(LayoutInflater.from(parent.getContext()),parent, viewType);
        if (holder==null){
            View view=LayoutInflater.from(context).inflate(resouse,null);
            holder=new ViewHolder(view);
        }
        return holder;
    }




    @Override
    public int getItemCount() {
        return msgs.size();
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder baseRecyclerViewHolder, final int position, Comment data) {
        final ViewHolder holder= (ViewHolder) baseRecyclerViewHolder;
        BmobQuery<User> query=new BmobQuery<>();
        String sql=String.format("select * from User where user_id='%s'",msgs.get(position).getComment().getUser_id());
        if (msgs.get(position).getUser()==null){
            query.doSQLQuery(sql, new SQLQueryListener<User>() {
                @Override
                public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        if (bmobQueryResult.getResults().size() > 0) {
                            User user = bmobQueryResult.getResults().get(0);
                            msgs.get(position).setUser(user);
                            initUser(holder,msgs.get(position).getUser());
                        }
                    } else {
                        Log.e("user", e.getMessage());
                    }
                }
            });
        }else {
            initUser(holder,msgs.get(position).getUser());
        }
        holder.commet_text.setText(msgs.get(position).getComment().getCommet_msg());
        holder.comment_date.setText(msgs.get(position).getComment().getCreatedAt());

    }


    private void initUser(final ViewHolder holder, User user){
        holder.user_name.setText(user.getUser_name());
        File cache_use_head = new File(preferencesUtil.getString("system_img_path") + "/" + user.getImg_name());
        if (cache_use_head.exists()) {
            Glide.with(context)
                    .load(preferencesUtil.getString("system_img_path") + "/" + user.getImg_name())
                    .into(holder.imageView);
        } else {
            final BmobFile user_img_file = new BmobFile(
                    user.getImg_name(),
                    user.getImg_group(),
                    user.getImg_url());
            user_img_file.download(cache_use_head,
                    new DownloadFileListener() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Glide.with(context)
                                        .load(preferencesUtil.getString("system_img_path") + "/" + user_img_file.getFilename())
                                       /* .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)*/
                                        .into(holder.imageView);
                            }
                        }

                        @Override
                        public void onProgress(Integer integer, long l) {

                        }
                    });
        }
    }




    public class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder{
        public CircleImageView imageView;
        public TextView commet_text,comment_date,user_name;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(CircleImageView)itemView.findViewById(R.id.user_head);
            comment_date=(TextView)itemView.findViewById(R.id.commet_date);
            commet_text=(TextView)itemView.findViewById(R.id.comment_msg);
            user_name=(TextView)itemView.findViewById(R.id.user_name);
        }
    }


    public void addHeadData(List<Comment> comments){
        for (int i=0;i<comments.size();i++){
            data.add(0,comments.get(i));
            Msg msg=new Msg();
            msg.setComment(comments.get(i));
            msgs.add(0,msg);
        }
        notifyDataSetChanged();
    }


    public void addFootData(List<Comment> comments){
        for (int i=0;i<comments.size();i++){
            data.add(comments.get(i));
            Msg msg=new Msg();
            msg.setComment(comments.get(i));
            msgs.add(msg);
        }
        notifyDataSetChanged();
    }



    public List<Comment>getData(){
        return data;
    }

    public class Msg{
        private User user;
        private Comment comment;



        public User getUser() {
            return user;
        }

        public Comment getComment() {
            return comment;
        }


        public void setUser(User user) {
            this.user = user;
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }
    }

}
