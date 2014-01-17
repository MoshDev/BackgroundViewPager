BackgroundViewPager
===================

Its similar to the Launcher Background effect, when user switch between fragments, the background will scroll within specific ratio to cover all fragments.

checkout the code, it includes a sample, just replace the background to any image you have.

Usage
==================
Simply add your "BackgroundViewPager" into your layout xml, and assign a drawable to it,

    <com.infusionapps.widget.BackgroundViewPager
        android:id="@+id/flowViewPager1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_centerInParent="true"
        flow:image="@drawable/bg5" />
