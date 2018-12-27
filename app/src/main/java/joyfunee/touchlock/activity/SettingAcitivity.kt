package joyfunee.touchlock.activity

import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_setting.*
import com.pes.androidmaterialcolorpickerdialog.ColorPicker
import joyfunee.touchlock.R


class SettingAcitivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        btnPickColor.setBackgroundColor(accentColorCode)

        btnPickColor.setOnClickListener {

            val cp =
                ColorPicker(this, accentColorCode / (0x0000), (accentColorCode / 0x00) % 0x00, accentColorCode % 0x00)
            cp.show()

            val okColor = cp.findViewById(R.id.okColorButton) as Button

            okColor.setOnClickListener {
                accentColorCode = cp.red * 0x0000 + cp.green * 0x00 + cp.blue

                cp.dismiss()

                savePreferences()
            }
        }
    }

    override fun savePreferences() {
        isPatternUnLock = rbDrawPattern.isChecked
        super.savePreferences()
    }
}
