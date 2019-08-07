# OverscrolllableNestedScrollView
Small custom view with smooth overscroll. You can add scale background


# Work example
![](https://github.com/vovaksenov99/OverscrollableScrollView/blob/master/c.gif)
![](https://github.com/vovaksenov99/OverscrollableScrollView/blob/master/b.gif)
![](https://github.com/vovaksenov99/OverscrollableScrollView/blob/master/a.gif)

# View params.

| Parameter | Description | units |
| ------------- | ------------- | ------------- |
| maxOverscrollDistance  | max overscroll distance  | dp |
| scaleCoefficient  | depends from current overscroll. Smaller the value -> increase scale.  | - |
| pullUpAnimationTime  | pull up animation duration  | ms |
| pullUpInterpolator  |  provides smooth pull up animation  | Interpolator |

*scaleView* - view which will be scaled when overscroll start (can be null)

*headerView* - view which can be above OverscrolllableNestedScrollView, like at the example (can be null)
