appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} %-5level [%5.15thread][%logger{0}] %m%n"
    }
}
logger("org.eclipse.jetty", INFO)
root(INFO, ["STDOUT"])