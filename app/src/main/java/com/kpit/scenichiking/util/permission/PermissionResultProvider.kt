package com.kpit.scenichiking.util.permission

interface PermissionResultProvider {
    fun onRationaleDenied(requestCode: Int)

    fun onRationaleAccepted(requestCode: Int)

    fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>)

    fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>)

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
}
