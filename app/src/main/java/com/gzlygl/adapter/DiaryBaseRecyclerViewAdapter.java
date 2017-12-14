package com.gzlygl.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.BaseRecyclerAdapter;
import com.gzlygl.R;
import com.gzlygl.javabeen.Collection;
import com.gzlygl.javabeen.Diary;
import com.gzlygl.javabeen.Img;
import com.gzlygl.javabeen.User;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.CommentActivity;
import com.gzlygl.view.activity.PreImageActivity;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DengJf on 2017/12/3.
 */

public class DiaryBaseRecyclerViewAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, Diary> {

    private List<Diary>data;
    private List<Msg>msgs;
    private Context context;
    private PreferencesUtil preferencesUtil;
    private Animation anim;
    private int resource;

    public DiaryBaseRecyclerViewAdapter(Context context, int resource, List<Diary> data) {
        super(data);
        this.data=data;
        this.context=context;
        this.resource=resource;
        preferencesUtil=new PreferencesUtil(context);
        msgs=new ArrayList<>();
        anim= AnimationUtils.loadAnimation(context,R.anim.alpha);
        for (int i=0;i<data.size();i++){
            Msg msg=new Msg();
            msg.setDiary(data.get(i));
            msgs.add(msg);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(resource,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final BaseRecyclerViewHolder baseRecyclerViewHolder, final int position, Diary data) {
        final ViewHolder holder= (ViewHolder) baseRecyclerViewHolder;
        BmobQuery<User> query=new BmobQuery<>();
        String sql=String.format("select * from User where user_id='%s'",msgs.get(position).getDiary().getUser_id());
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

        holder.diary_msg.setText(msgs.get(position).getDiary().getDiary_msg());
        holder.diary_date.setText(msgs.get(position).getDiary().getCreatedAt());
        if (msgs.get(position).getImgs()==null){
            BmobQuery<Img>imgQuery=new BmobQuery<>();
            imgQuery.addWhereEqualTo("diary_id",msgs.get(position).getDiary().getObjectId());
            imgQuery.findObjects(new FindListener<Img>() {
                @Override
                public void done(List<Img> list, BmobException e) {
                    if (e==null){
                        initImgs(holder,list,msgs.get(position).getDiary());
                    }else {
                        Log.e("gzlygl",e.getMessage());
                    }
                }
            });
        }else {
            initImgs(holder,msgs.get(position).getImgs(),msgs.get(position).getDiary());
        }
        holder.comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.comment_btn.startAnimation(anim);
                Intent intent=new Intent(context, CommentActivity.class);
                intent.putExtra("ObjectId",msgs.get(position).getDiary().getObjectId());
                intent.putExtra("Diary_date",msgs.get(position).getDiary().getCreatedAt());
                intent.putExtra("Diary_msg",msgs.get(position).getDiary().getDiary_msg());
                intent.putExtra("Img_num",msgs.get(position).getDiary().getImg_num());
                intent.putExtra("User_id",msgs.get(position).getDiary().getUser_id());
                context.startActivity(intent);
            }
        });

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showCollectionDialog(position);
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public class ViewHolder extends BaseRecyclerViewHolder {
        CircleImageView user_head;
        TextView user_name;
        TextView diary_msg;
        TextView diary_date;
        Button comment_btn;
        LinearLayout three_layout;
        LinearLayout six_layout;
        LinearLayout nine_layout;
        ImageView img_1;
        ImageView img_2;
        ImageView img_3;
        ImageView img_4;
        ImageView img_5;
        ImageView img_6;
        ImageView img_7;
        ImageView img_8;
        ImageView img_9;
        ImageView one_img;
        LinearLayout layout;
        public ViewHolder(View view) {
            super(view);
            user_head=(CircleImageView)view.findViewById(R.id.user_head);
            user_name=(TextView)view.findViewById(R.id.user_name);
            diary_msg=(TextView)view.findViewById(R.id.diary);
            diary_date=(TextView)view.findViewById(R.id.diary_date);
            comment_btn=(Button)view.findViewById(R.id.comment);
            three_layout=(LinearLayout)view.findViewById(R.id.three_img_layout);
            six_layout=(LinearLayout)view.findViewById(R.id.six_img_layout);
            nine_layout=(LinearLayout)view.findViewById(R.id.nine_img_layout);
            img_1=(ImageView)view.findViewById(R.id.img_1);
            img_2=(ImageView)view.findViewById(R.id.img_2);
            img_3=(ImageView)view.findViewById(R.id.img_3);
            img_4=(ImageView)view.findViewById(R.id.img_4);
            img_5=(ImageView)view.findViewById(R.id.img_5);
            img_6=(ImageView)view.findViewById(R.id.img_6);
            img_7=(ImageView)view.findViewById(R.id.img_7);
            img_8=(ImageView)view.findViewById(R.id.img_8);
            img_9=(ImageView)view.findViewById(R.id.img_9);
            one_img=(ImageView)view.findViewById(R.id.one_img);
            layout=(LinearLayout)view.findViewById(R.id.layout);
        }
    }


    private void showImg(final ImageView imageView, final Img img){
        final String filePath=preferencesUtil.getString("system_img_path") + "/"+img.getImg_name();
        File file=new File(filePath);
        if (file.exists()){
            Glide.with(context)
                    .load(filePath)
                    .into(imageView);
        }else {
            BmobFile bmobFile=new BmobFile(img.getImg_name(),img.getImg_group(),img.getImg_url());
            bmobFile.download(file, new DownloadFileListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e==null){
                        Glide.with(context)
                                .load(filePath)
                                .into(imageView);
                    }else {
                        showImg(imageView,img);
                    }
                }

                @Override
                public void onProgress(Integer integer, long l) {

                }
            });

        }
    }

    private void initUser(final ViewHolder holder, User user){
        holder.user_name.setText(user.getUser_name());
        File cache_use_head = new File(preferencesUtil.getString("system_img_path") + "/" + user.getImg_name());
        if (cache_use_head.exists()) {
            Glide.with(context)
                    .load(preferencesUtil.getString("system_img_path") + "/" + user.getImg_name())
                    .into(holder.user_head);
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
                                        .into(holder.user_head);
                            }
                        }

                        @Override
                        public void onProgress(Integer integer, long l) {

                        }
                    });
        }
    }

    private void initImgs(ViewHolder holder, final List<Img>list, Diary diary){
        switch (list.size()){
            case 1:
                showImg(holder.one_img,list.get(0));
                break;
            case 2:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                break;
            case 3:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                showImg(holder.img_3,list.get(2));
                break;
            case 4:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                showImg(holder.img_3,list.get(2));
                showImg(holder.img_4,list.get(3));
                break;
            case 5:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                showImg(holder.img_3,list.get(2));
                showImg(holder.img_4,list.get(3));
                showImg(holder.img_5,list.get(4));
                break;
            case 6:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                showImg(holder.img_3,list.get(2));
                showImg(holder.img_4,list.get(3));
                showImg(holder.img_5,list.get(4));
                showImg(holder.img_6,list.get(5));
                break;
            case 7:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                showImg(holder.img_3,list.get(2));
                showImg(holder.img_4,list.get(3));
                showImg(holder.img_5,list.get(4));
                showImg(holder.img_6,list.get(5));
                showImg(holder.img_7,list.get(6));
                break;
            case 8:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                showImg(holder.img_3,list.get(2));
                showImg(holder.img_4,list.get(3));
                showImg(holder.img_5,list.get(4));
                showImg(holder.img_6,list.get(5));
                showImg(holder.img_7,list.get(6));
                showImg(holder.img_8,list.get(7));
                break;
            case 9:
                showImg(holder.img_1,list.get(0));
                showImg(holder.img_2,list.get(1));
                showImg(holder.img_3,list.get(2));
                showImg(holder.img_4,list.get(3));
                showImg(holder.img_5,list.get(4));
                showImg(holder.img_6,list.get(5));
                showImg(holder.img_7,list.get(6));
                showImg(holder.img_8,list.get(7));
                showImg(holder.img_9,list.get(8));
                break;
        }

        switch (Integer.parseInt(diary.getImg_num())){
            case 0:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.GONE);
                holder.six_layout.setVisibility(View.GONE);
                holder.nine_layout.setVisibility(View.GONE);
                break;
            case 1:
                holder.one_img.setVisibility(View.VISIBLE);
                holder.three_layout.setVisibility(View.GONE);
                holder.six_layout.setVisibility(View.GONE);
                holder.nine_layout.setVisibility(View.GONE);
                break;
            case 2:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.GONE);
                holder.nine_layout.setVisibility(View.GONE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.GONE);
                holder.nine_layout.setVisibility(View.GONE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.VISIBLE);
                holder.nine_layout.setVisibility(View.GONE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.VISIBLE);
                holder.img_4.setVisibility(View.VISIBLE);
                holder.img_5.setVisibility(View.INVISIBLE);
                holder.img_6.setVisibility(View.INVISIBLE);
                break;
            case 5:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.VISIBLE);
                holder.nine_layout.setVisibility(View.GONE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.VISIBLE);
                holder.img_4.setVisibility(View.VISIBLE);
                holder.img_5.setVisibility(View.VISIBLE);
                holder.img_6.setVisibility(View.INVISIBLE);
                break;
            case 6:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.VISIBLE);
                holder.nine_layout.setVisibility(View.GONE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.VISIBLE);
                holder.img_4.setVisibility(View.VISIBLE);
                holder.img_5.setVisibility(View.VISIBLE);
                holder.img_6.setVisibility(View.VISIBLE);
                break;
            case 7:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.VISIBLE);
                holder.nine_layout.setVisibility(View.VISIBLE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.VISIBLE);
                holder.img_4.setVisibility(View.VISIBLE);
                holder.img_5.setVisibility(View.VISIBLE);
                holder.img_6.setVisibility(View.VISIBLE);
                holder.img_7.setVisibility(View.VISIBLE);
                holder.img_8.setVisibility(View.INVISIBLE);
                holder.img_9.setVisibility(View.INVISIBLE);
                break;
            case 8:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.VISIBLE);
                holder.nine_layout.setVisibility(View.VISIBLE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.VISIBLE);
                holder.img_4.setVisibility(View.VISIBLE);
                holder.img_5.setVisibility(View.VISIBLE);
                holder.img_6.setVisibility(View.VISIBLE);
                holder.img_7.setVisibility(View.VISIBLE);
                holder.img_8.setVisibility(View.VISIBLE);
                holder.img_9.setVisibility(View.INVISIBLE);
                break;
            case 9:
                holder.one_img.setVisibility(View.GONE);
                holder.three_layout.setVisibility(View.VISIBLE);
                holder.six_layout.setVisibility(View.VISIBLE);
                holder.nine_layout.setVisibility(View.VISIBLE);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.VISIBLE);
                holder.img_4.setVisibility(View.VISIBLE);
                holder.img_5.setVisibility(View.VISIBLE);
                holder.img_6.setVisibility(View.VISIBLE);
                holder.img_7.setVisibility(View.VISIBLE);
                holder.img_8.setVisibility(View.VISIBLE);
                holder.img_9.setVisibility(View.VISIBLE);
                break;

        }
        holder.img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",1);
                context.startActivity(intent);
            }
        });

        holder.one_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",1);
                context.startActivity(intent);
            }
        });
        holder.img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",2);
                context.startActivity(intent);
            }
        });
        holder.img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",3);
                context.startActivity(intent);
            }
        });
        holder.img_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",4);
                context.startActivity(intent);
            }
        });
        holder.img_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",5);
                context.startActivity(intent);
            }
        });
        holder.img_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",6);
                context.startActivity(intent);
            }
        });
        holder.img_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",7);
                context.startActivity(intent);
            }
        });
        holder.img_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",8);
                context.startActivity(intent);
            }
        });
        holder.img_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PreImageActivity.class);
                ArrayList<String>filePaths=new ArrayList<String>();
                for (int i=0;i<list.size();i++){
                    filePaths.add(preferencesUtil.getString("system_img_path") + "/"+list.get(i).getImg_name());
                }
                intent.putStringArrayListExtra("imgs", filePaths);
                intent.putExtra("position",9);
                context.startActivity(intent);
            }
        });


    }

    private void showCollectionDialog(final int position) {
        final String items[] = {"收藏", "取消收藏"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Collection collection1= new Collection(
                                preferencesUtil.getString("user_id"),
                                data.get(position).getObjectId(),
                                data.get(position).getDiary_msg(),
                                data.get(position).getUser_id(),
                                data.get(position).getImg_num(),
                                data.get(position).getCreatedAt()
                        );
                        collection1.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                    case 1:
                        BmobQuery<Collection>query=new BmobQuery<Collection>();
                        query.addWhereEqualTo("collection_user_id",preferencesUtil.getString("user_id"));
                        query.addWhereEqualTo("diary_id",data.get(position).getObjectId());
                        query.findObjects(new FindListener<Collection>() {
                            @Override
                            public void done(List<Collection> list, BmobException e) {
                                if (e==null){
                                    for (int i=0;i<list.size();i++){
                                        Collection c=new Collection();
                                        c.setObjectId(list.get(i).getObjectId());
                                        c.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e==null){
                                                    Toast.makeText(context,"取消收藏成功",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }else {
                                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    public void addData(List<Diary>lists){
        for (int i=0;i<lists.size();i++){
            data.add(lists.get(i));
            Msg msg=new Msg();
            msg.setDiary(lists.get(i));
            msgs.add(msg);
        }
        notifyDataSetChanged();
    }

    public List<Diary> getData(){
        return data;
    }

    public class Msg{
        public List<Img>Imgs;
        public User user;
        public Diary diary;


        public List<Img> getImgs() {
            return Imgs;
        }

        public User getUser() {
            return user;
        }

        public Diary getDiary() {
            return diary;
        }

        public void setImgs(List<Img> imgs) {
            Imgs = imgs;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void setDiary(Diary diary) {
            this.diary = diary;
        }
    }
}
