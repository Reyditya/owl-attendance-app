package id.owl.com.owlliveattendanceapp.views.fragment.attendance

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.owl.com.owlliveattendanceapp.R
import id.owl.com.owlliveattendanceapp.databinding.FragmentAttendanceBinding
import id.owl.com.owlliveattendanceapp.dialog.MyDialog


class AttendanceFragment : Fragment(), OnMapReadyCallback {
    companion object{
        private const val REQUEST_CODE_MAP_PERMISSIONS = 1000
    }

    private val mapPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private var mapAttendance: SupportMapFragment? = null
    private var map: GoogleMap? = null
    private var binding: FragmentAttendanceBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMaps()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_MAP_PERMISSIONS -> {
                var isHasPermission = false
                val permissionNotGranted = StringBuilder()

                for (i in permissions.indices){
                    isHasPermission = grantResults[i] == PackageManager.PERMISSION_GRANTED

                    if (!isHasPermission){
                        permissionNotGranted.append("${permissions[i]}\n")
                    }
                }

                if (isHasPermission){
                    setupMaps()
                }else{
                    val message = permissionNotGranted.toString() + "\n" + getString(R.string.not_granted)
                    MyDialog.dynamicDialog(context, getString(R.string.required_permission), message)
                }
            }
        }
    }

    private fun setupMaps() {
        mapAttendance = childFragmentManager.findFragmentById(R.id.map_attendance) as SupportMapFragment
        mapAttendance?.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        if (checkPermission()) {
            //Coordinate Kantor Codepolitan
            val codepolitan = LatLng(-6.879513, 107.590085)
            map?.moveCamera(CameraUpdateFactory.newLatLng(codepolitan))
            map?.animateCamera(CameraUpdateFactory.zoomTo(20f))

            goToCurrentLocation()
        } else {
            setRequestPermission()
        }
    }

    private fun setRequestPermission() {
        requestPermissions(mapPermissions, REQUEST_CODE_MAP_PERMISSIONS)

    }

    private fun goToCurrentLocation() {

    }

    private fun checkPermission(): Boolean {
        var isHasPermission = false
        context?.let {
            for (permission in mapPermissions){
                isHasPermission = ActivityCompat.checkSelfPermission(it, permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        return isHasPermission
    }

}