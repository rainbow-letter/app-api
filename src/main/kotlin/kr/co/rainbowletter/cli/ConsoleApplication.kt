package kr.co.rainbowletter.cli

import kr.co.rainbowletter.cli.command.HelpCommand
import kr.co.rainbowletter.cli.command.MigrateCommand
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import picocli.CommandLine


@SpringBootApplication
@CommandLine.Command(
    name = "app",
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

        System.exit(result)
    }
}

fun main(args: Array<String>) {
    runApplication<ConsoleApplication>(*args)
}
