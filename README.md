# AnimateRepository
各种动画效果实现的集合

动画1：

实现要点：<br>
ImageView在设置属性动画时，对View.Y这个属性没有动画效果，需要对这个属性动画添加listener，在listener的回调中通过setY的方法来更新ImageView的位置；<br>
三阶贝塞尔曲线的运用；<br>
