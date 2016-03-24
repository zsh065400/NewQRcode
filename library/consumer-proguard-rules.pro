# keep getters/setters in RotatingDrawable so that animations can still work.
-keepclassmembers class net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu$RotatingDrawable {
   void set*(***);
   *** get*();
}
