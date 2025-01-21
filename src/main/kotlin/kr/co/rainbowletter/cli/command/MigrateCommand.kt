package kr.co.rainbowletter.cli.command

import org.springframework.stereotype.Component
import picocli.CommandLine

@CommandLine.Command(name = "migrate", description = ["Main application command"])
@Component
class MigrateCommand : Runnable {
    override fun run() {
        println("runnable")
    }
}