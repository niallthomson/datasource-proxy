DataSource Proxy
================

What?
-----

This is a fork of the [datasource-proxy project](http://code.google.com/p/datasource-proxy/) by Tadaya Tsuyukubo.

The aim of the project is to provide a proxy mechanism which provides some hookpoints for collecting 
information about database query operations for various purposes. This has been used to implement query 
logging which can write to a number of different logging providers:

- Standard Out
- SLF4J
- Apache Commons

My current goals are:

- Refactor some of the existing code the reduce code duplication
- Make the existing functionality (listeners, filters, interceptors etc.) more configurable
- Add some extra functionality

Improvements
------------

Some stuff that you do in this fork over the original are:

- Specify the format of log messages
- Use aroundQuery in listeners, similar to AOP (instead of just before and after). Useful for profiling.
- Extend existing functionality by injecting dependencies
- Query strings in log messages have the arguments substituted inline

A lot of time has also been spent tidying up the code and refactoring it so theres less duplication. There are also more unit tests (77% code coverage).