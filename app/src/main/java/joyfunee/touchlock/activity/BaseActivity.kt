package joyfunee.touchlock.activity

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import joyfunee.touchlock.Common
import joyfunee.touchlock.R

open class BaseActivity : AppCompatActivity() {

    val neededPermission = arrayOf(Manifest.permission.INTERNET)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (needRequestPermissions())
            requestNeededPermissions()
        else initAll()
    }

    private fun initAll() {
        loadPreferences()
        initUI()
    }

    open fun initUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        lateinit var pref: SharedPreferences
    }

    var isPatternUnLock: Boolean = false
    var accentColorCode: Int = 0x000000


    open fun loadPreferences() {
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        isPatternUnLock = pref.getBoolean(Common.PATTERN_UNLOCK, false)
        accentColorCode = pref.getInt(
            Common.ACCENT_COLOR, resources.getColor(
                R.color.colorAccent
            ))
    }

    open fun savePreferences() {
        pref.edit().putBoolean(Common.PATTERN_UNLOCK, isPatternUnLock).putInt(
            Common.ACCENT_COLOR, accentColorCode)
            .commit()
    }


    private val REQUEST_PERMISSION_CODE: Int = 100

    private fun requestNeededPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(neededPermission, REQUEST_PERMISSION_CODE)

    }

    private fun needRequestPermissions(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            for (permission in neededPermission) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                    return true
            }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (needRequestPermissions()) {
                Toast.makeText(this, R.string.permission_deny_close_app, Toast.LENGTH_SHORT).show()
                finish()
            }

        } else {
            initAll()
        }
    }
}