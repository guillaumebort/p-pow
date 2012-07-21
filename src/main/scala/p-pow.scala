object PPow {

	import java.io._
	import java.net._

	val EPHEMERAL = 0

	lazy val register = (name: String) => {
		(address: InetSocketAddress) => {
            val home = new File(System.getProperty("user.home"))
            val pow = new File(home, ".pow")
            if(pow.exists) {
	            val app = new File(pow, name)
	            val out = new FileOutputStream(app)
	            out.write(address.getPort.toString.getBytes)
	            out.close()
            }
        }: Unit 
	}

	lazy val deregister = (name: String) => {
		() => {
            val home = new File(System.getProperty("user.home"))
            val pow = new File(home, ".pow")
            if(pow.exists) {
	            val app = new File(pow, name)
	            app.delete()
	        }
        }: Unit
	}

}
