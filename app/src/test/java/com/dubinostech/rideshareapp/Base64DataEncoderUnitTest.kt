package com.dubinostech.rideshareapp

import java.io.BufferedInputStream
import java.io.FileInputStream


 class Base64DataEncoderUnitTest()  {




    fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println(
            "Usage: java com.oreilly.servlet.Base64Encoder fileToEncode"
        )
        return
    }

    var encoder: Base64DataEncoder? = null
    var `buffer`: BufferedInputStream? = null
    try {
        encoder = Base64DataEncoder(System.out)
        buffer = BufferedInputStream(FileInputStream(args[0]))

        val buf = ByteArray(4 * 1024)  // 4K buffer
        var bytesRead: Int
        bytesRead = buffer.read(buf)
        while ((bytesRead) != -1) {
            encoder.write(buf, 0, bytesRead)
            bytesRead = buffer.read(buf)
        }
    } finally {
        buffer?.close()
        encoder?.close()
    }
}

}