package kr.co.rainbowletter.cli.command

import org.springframework.stereotype.Component
import picocli.CommandLine

@CommandLine.Command(name = "app", description = ["Main application command"])
@Component
class HelpCommand : Runnable {
    override fun run() {
        println("도움")
    }
}