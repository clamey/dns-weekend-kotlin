import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

const val DNS_SERVER_PORT = 53

fun main(args: Array<String>) {
    println("Hello World!")
    val ipAddress = InetAddress.getByName("1.1.1.1")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val dnsFrame = DNSHeader().toByteArray() + DNSQuestion("www.example.com").toByteArray()

    println("Sending: " + dnsFrame.size + " bytes")
    println(dnsFrame.toHex())

    val socket = DatagramSocket()
    val dnsReqPacket = DatagramPacket(dnsFrame, dnsFrame.size, ipAddress, DNS_SERVER_PORT)
    socket.send(dnsReqPacket)
}