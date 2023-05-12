import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.random.Random

const val DNS_SERVER_PORT = 53

const val TYPE_A = 1
const val CLASS_IN = 1

fun main(args: Array<String>) {
    println("Hello World!")
    val ipAddress = InetAddress.getByName("1.1.1.1")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val query = buildQuery("www.example.com", TYPE_A)

    println("Sending: " + query.size + " bytes")
    println(query.toHex())

    val socket = DatagramSocket()
    val dnsReqPacket = DatagramPacket(query, query.size, ipAddress, DNS_SERVER_PORT)
    socket.send(dnsReqPacket)
}

fun buildQuery(domain: String, recordType: Int): ByteArray =
    DNSHeader(
        id = Random.nextInt(),
        flags = bitset {
            set(8)
        }
    ).toByteArray() + DNSQuestion(
        name = domain,
        type_ = recordType,
        class_ = CLASS_IN
    ).toByteArray()