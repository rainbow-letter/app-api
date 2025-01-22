package kr.co.rainbowletter.cli

import kr.co.rainbowletter.cli.command.HelpCommand
import kr.co.rainbowletter.cli.command.MigrateCommand
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import picocli.CommandLine
import kotlin.system.exitProcess


@SpringBootApplication(scanBasePackages = ["kr.co.rainbowletter"])
@CommandLine.Command(
    name = "migrate",
    subcommands = []
)
class ConsoleApplication(
    private val helpCommand: HelpCommand,
    private val migrateCommand: MigrateCommand,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val result = CommandLine(helpCommand)
            .addSubcommand("migrate", migrateCommand)
            .execute(*args)

        exitProcess(result)
    }
}

fun main(args: Array<String>) {
    runApplication<ConsoleApplication>(*args)
}
