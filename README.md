# AnimateRepository
各种动画效果实现的集合

动画1：

实现要点：<br>
ImageView在设置属性动画时，对View.Y这个属性没有动画效果，需要对这个属性动画添加listener，在listener的回调中通过setY的方法来更新ImageView的位置；<br>
三阶贝塞尔曲线的运用；<br><br><br>


动画2：
加入了4个属性：
waterColor:图案色彩；<br>
waterHeight：水位高度；<br>
waveSwing：水波振幅；<br>
circleRadius：圆半径；<br>

实现要点：<br>
用正弦函数的波形来模拟水波，绘制的图形是由宽度为1的无数个竖条组成;<br>
通过属性动画，修改起始角度即可改变波形；<br>
在Bitmap上画出波形，再生成Shader，从而达到绘制圆形的水波图案。<br>