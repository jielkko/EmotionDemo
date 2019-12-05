package com.hjl.emotiondemo.common;


import android.app.Application;

import com.hjl.emotiondemo.R;
import com.hjl.emotionpicker.model.EmotionModel;
import com.hjl.emotionpicker.utils.EmotionPicker;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private Boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();


        //初始化  建议在Application 调用
        //自定义表情包
        initEmotion();

    }

    private void initEmotion() {
        //初始化  建议在Application 调用
        List<EmotionModel> lists = new ArrayList<>();
        lists.add(new EmotionModel("[龇牙]", R.drawable.d_ziya));
        lists.add(new EmotionModel("[调皮]", R.drawable.d_diaopi));
        lists.add(new EmotionModel("[流汗]", R.drawable.d_liuhan));
        lists.add(new EmotionModel("[偷笑]", R.drawable.d_touxiao));


        lists.add(new EmotionModel("[再见]", R.drawable.d_zaijian));
        lists.add(new EmotionModel("[敲打]", R.drawable.d_qiaoda));
        lists.add(new EmotionModel("[擦汗]", R.drawable.d_cahan));
        lists.add(new EmotionModel("[猪头]", R.drawable.d_zhutou));


        lists.add(new EmotionModel("[玫瑰]", R.drawable.d_meigui));
        lists.add(new EmotionModel("[流泪]", R.drawable.d_liulei));
        lists.add(new EmotionModel("[大哭]", R.drawable.d_daku));
        lists.add(new EmotionModel("[嘘]", R.drawable.d_xu));


        lists.add(new EmotionModel("[酷]", R.drawable.d_ku));
        lists.add(new EmotionModel("[抓狂]", R.drawable.d_zhuakuang));
        lists.add(new EmotionModel("[委屈]", R.drawable.d_weiqu));
        lists.add(new EmotionModel("[便便]", R.drawable.d_bianbian));


        lists.add(new EmotionModel("[炸弹]", R.drawable.d_zhadan));
        lists.add(new EmotionModel("[菜刀]", R.drawable.d_caidao));
        lists.add(new EmotionModel("[可爱]", R.drawable.d_keai));
        lists.add(new EmotionModel("[色]", R.drawable.d_se));


        lists.add(new EmotionModel("[害羞]", R.drawable.d_haixiu));
        lists.add(new EmotionModel("[得意]", R.drawable.d_deyi));
        lists.add(new EmotionModel("[吐]", R.drawable.d_tu));
        lists.add(new EmotionModel("[微笑]", R.drawable.d_weixiao));


        lists.add(new EmotionModel("[怒]", R.drawable.d_nu));
        lists.add(new EmotionModel("[尴尬]", R.drawable.d_ganga));
        lists.add(new EmotionModel("[惊恐]", R.drawable.d_jingkong));
        lists.add(new EmotionModel("[冷汗]", R.drawable.d_lenghan));


        lists.add(new EmotionModel("[爱心]", R.drawable.d_aixin));
        lists.add(new EmotionModel("[示爱]", R.drawable.d_shiai));
        lists.add(new EmotionModel("[白眼]", R.drawable.d_baiyan));
        lists.add(new EmotionModel("[傲慢]", R.drawable.d_aoman));


        lists.add(new EmotionModel("[难过]", R.drawable.d_nanguo));
        lists.add(new EmotionModel("[惊讶]", R.drawable.d_jingya));
        lists.add(new EmotionModel("[疑问]", R.drawable.d_yiwen));
        lists.add(new EmotionModel("[困]", R.drawable.d_kun));


        lists.add(new EmotionModel("[么么哒]", R.drawable.d_memeda));
        lists.add(new EmotionModel("[憨笑]", R.drawable.d_hanxiao));
        lists.add(new EmotionModel("[爱情]", R.drawable.d_aiqing));
        lists.add(new EmotionModel("[衰]", R.drawable.d_shuai));

        lists.add(new EmotionModel("[撇嘴]", R.drawable.d_piezui));
        lists.add(new EmotionModel("[阴险]", R.drawable.d_yinxian));
        lists.add(new EmotionModel("[奋斗]", R.drawable.d_fendou));
        lists.add(new EmotionModel("[发呆]", R.drawable.d_fadai));


        lists.add(new EmotionModel("[右哼哼]", R.drawable.d_youhengheng));
        lists.add(new EmotionModel("[抱抱]", R.drawable.d_baobao));
        lists.add(new EmotionModel("[坏笑]", R.drawable.d_huaixiao));
        lists.add(new EmotionModel("[飞吻]", R.drawable.d_feiwen));


        lists.add(new EmotionModel("[鄙视]", R.drawable.d_bishi));
        lists.add(new EmotionModel("[晕]", R.drawable.d_yun));
        lists.add(new EmotionModel("[大兵]", R.drawable.d_dabing));
        lists.add(new EmotionModel("[可怜]", R.drawable.d_kelian));


        lists.add(new EmotionModel("[强]", R.drawable.d_qiang));
        lists.add(new EmotionModel("[弱]", R.drawable.d_ruo));
        lists.add(new EmotionModel("[握手]", R.drawable.d_woshou));
        lists.add(new EmotionModel("[胜利]", R.drawable.d_shengli));


        lists.add(new EmotionModel("[抱拳]", R.drawable.d_baoquan));
        lists.add(new EmotionModel("[凋谢]", R.drawable.d_diaoxie));
        lists.add(new EmotionModel("[米饭]", R.drawable.d_mifan));
        lists.add(new EmotionModel("[蛋糕]", R.drawable.d_dangao));


        lists.add(new EmotionModel("[西瓜]", R.drawable.d_xigua));
        lists.add(new EmotionModel("[啤酒]", R.drawable.d_pijiu));
        lists.add(new EmotionModel("[瓢虫]", R.drawable.d_piaochong));
        lists.add(new EmotionModel("[勾引]", R.drawable.d_gouyin));


        lists.add(new EmotionModel("[OK]", R.drawable.d_ok));
        lists.add(new EmotionModel("[爱你]", R.drawable.d_aini));
        lists.add(new EmotionModel("[咖啡]", R.drawable.d_kafei));
        lists.add(new EmotionModel("[月亮]", R.drawable.d_yueliang));


        lists.add(new EmotionModel("[刀]", R.drawable.d_dao));
        lists.add(new EmotionModel("[发抖]", R.drawable.d_fadou));
        lists.add(new EmotionModel("[差劲]", R.drawable.d_chajin));
        lists.add(new EmotionModel("[拳头]", R.drawable.d_quantou));


        lists.add(new EmotionModel("[心碎了]", R.drawable.d_xinsuile));
        lists.add(new EmotionModel("[太阳]", R.drawable.d_taiyang));
        lists.add(new EmotionModel("[礼物]", R.drawable.d_liwu));
        lists.add(new EmotionModel("[皮球]", R.drawable.d_piqiu));


        lists.add(new EmotionModel("[骷髅]", R.drawable.d_kulou));
        lists.add(new EmotionModel("[挥手]", R.drawable.d_huishou));
        lists.add(new EmotionModel("[闪电]", R.drawable.d_shandian));
        lists.add(new EmotionModel("[饥饿]", R.drawable.d_jie));


        lists.add(new EmotionModel("[咒骂]", R.drawable.d_zhouma));
        lists.add(new EmotionModel("[折磨]", R.drawable.d_zhemo));
        lists.add(new EmotionModel("[抠鼻]", R.drawable.d_koubi));
        lists.add(new EmotionModel("[鼓掌]", R.drawable.d_guzhang));


        lists.add(new EmotionModel("[糗大了]", R.drawable.d_qiudale));
        lists.add(new EmotionModel("[左哼哼]", R.drawable.d_zuohengheng));
        lists.add(new EmotionModel("[打哈欠]", R.drawable.d_dahaqian));
        lists.add(new EmotionModel("[快哭了]", R.drawable.d_kuaikule));


        lists.add(new EmotionModel("[吓]", R.drawable.d_xia));
        lists.add(new EmotionModel("[篮球]", R.drawable.d_lanqiu));
        lists.add(new EmotionModel("[乒乓]", R.drawable.d_pingpang));
        lists.add(new EmotionModel("[NO]", R.drawable.d_no));


        lists.add(new EmotionModel("[跳跳]", R.drawable.d_tiaotiao));
        lists.add(new EmotionModel("[怄火]", R.drawable.d_ouhuo));
        lists.add(new EmotionModel("[转圈]", R.drawable.d_zhuanquan));
        lists.add(new EmotionModel("[磕头]", R.drawable.d_ketou));


        lists.add(new EmotionModel("[回头]", R.drawable.d_huitou));
        lists.add(new EmotionModel("[跳绳]", R.drawable.d_tiaosheng));
        lists.add(new EmotionModel("[激动]", R.drawable.d_jidong));
        lists.add(new EmotionModel("[街舞]", R.drawable.d_jiewu));


        lists.add(new EmotionModel("[献吻]", R.drawable.d_xianwen));
        lists.add(new EmotionModel("[左太极]", R.drawable.d_zuotaiji));
        lists.add(new EmotionModel("[右太极]", R.drawable.d_youtaiji));
        lists.add(new EmotionModel("[闭嘴]", R.drawable.d_bizui));


        lists.add(new EmotionModel("[猫咪]", R.drawable.d_maomi));
        lists.add(new EmotionModel("[红双喜]", R.drawable.d_hongshuangxi));
        lists.add(new EmotionModel("[鞭炮]", R.drawable.d_bianpao));
        lists.add(new EmotionModel("[红灯笼]", R.drawable.d_hongdenglong));


        lists.add(new EmotionModel("[麻将]", R.drawable.d_majiang));
        lists.add(new EmotionModel("[麦克风]", R.drawable.d_maikefeng));
        lists.add(new EmotionModel("[礼品袋]", R.drawable.d_lipindai));
        lists.add(new EmotionModel("[信封]", R.drawable.d_xinfeng));


        lists.add(new EmotionModel("[象棋]", R.drawable.d_xiangqi));
        lists.add(new EmotionModel("[彩带]", R.drawable.d_caidai));
        lists.add(new EmotionModel("[蜡烛]", R.drawable.d_lazhu));
        lists.add(new EmotionModel("[爆筋]", R.drawable.d_baojin));


        lists.add(new EmotionModel("[棒棒糖]", R.drawable.d_bangbangtang));
        lists.add(new EmotionModel("[奶瓶]", R.drawable.d_naiping));
        lists.add(new EmotionModel("[面条]", R.drawable.d_miantiao));
        lists.add(new EmotionModel("[香蕉]", R.drawable.d_xiangjiao));


        lists.add(new EmotionModel("[飞机]", R.drawable.d_feiji));
        lists.add(new EmotionModel("[左车头]", R.drawable.d_zuochetou));
        lists.add(new EmotionModel("[车厢]", R.drawable.d_chexiang));
        lists.add(new EmotionModel("[右车头]", R.drawable.d_youchetou));


        lists.add(new EmotionModel("[多云]", R.drawable.d_duoyun));
        lists.add(new EmotionModel("[下雨]", R.drawable.d_xiayu));
        lists.add(new EmotionModel("[钞票]", R.drawable.d_chaopiao));
        lists.add(new EmotionModel("[熊猫]", R.drawable.d_xiongmao));


        lists.add(new EmotionModel("[灯泡]", R.drawable.d_dengpao));
        lists.add(new EmotionModel("[风车]", R.drawable.d_fengche));
        lists.add(new EmotionModel("[闹钟]", R.drawable.d_naozhong));
        lists.add(new EmotionModel("[彩球]", R.drawable.d_caiqiu));


        lists.add(new EmotionModel("[钻戒]", R.drawable.d_zuanjie));
        lists.add(new EmotionModel("[沙发]", R.drawable.d_shafa));
        lists.add(new EmotionModel("[纸巾]", R.drawable.d_zhijin));
        lists.add(new EmotionModel("[手枪]", R.drawable.d_shouqiang));


        lists.add(new EmotionModel("[青蛙]", R.drawable.d_qingwa));

        EmotionPicker.getInstance().setEmotionList(lists);
    }

}