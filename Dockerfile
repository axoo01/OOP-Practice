FROM eclipse-temurin:23-jdk

WORKDIR /app


COPY src/ src/
RUN mkdir -p target \
    && find src -name "*.java" > sources.txt \
    && javac -d target @sources.txt


RUN rm -rf src/ sources.txt


COPY run.sh /app/run.sh
RUN chmod +x run.sh

ENTRYPOINT ["./run.sh"]


