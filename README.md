# AnimateRepository
各种动画效果实现的集合

动画1：<br>
![Demo GIF](https://github.com/zzjivan/AnimateRepository/raw/master/gif/heart_fly_up.gif "Demo") 
<br>

实现要点：<br>
ImageView在设置属性动画时，对View.Y这个属性没有动画效果，需要对这个属性动画添加listener，在listener的回调中通过setY的方法来更新ImageView的位置；<br>
三阶贝塞尔曲线的运用；<br><br>


动画2：<br>
![Demo GIF](https://github.com/zzjivan/AnimateRepository/raw/master/gif/wave.gif "Demo") 
<br>

加入了4个属性：
waterColor:图案色彩；<br>
waterHeight：水位高度；<br>
waveSwing：水波振幅；<br>
circleRadius：圆半径；<br>

实现要点：<br>
用正弦函数的波形来模拟水波，绘制的图形是由宽度为1的无数个竖条组成;<br>
通过属性动画，修改起始角度即可改变波形；<br>
在Bitmap上画出波形，再生成Shader，从而达到绘制圆形的水波图案。<br>

动画3：略。。

动画4：仿即刻点赞效果<br>
![Demo GIF](https://github.com/zzjivan/AnimateRepository/raw/master/gif/thumbup.gif "Demo") 
<br>

实现思路：<br>
分成两部分实现：左侧的icon部分，右侧的text部分。<br>
icon部分：好像没什么说的，绘制bitmap。为了后续动画效果，留一个“圆环半径”属性。顶部的shining图片的显示也通过这个“圆环半径”来控制。
text部分：当设置了点赞数count后，可以立即计算出需要动画处理的位数。整个text需要drawText三次：绘制不动的部分；绘制点赞前数字的动画<br>
部分；绘制点赞后数字的动画部分； 上下移动的效果通过“offsetY”属性来控制,消失出现通过“textAlpha”属性控制。<br>

         
