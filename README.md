# Kaldi Speech-to-Text REST Service

Service for easy access to speech recognition capabilities of Kaldi using REST interface.
Simple deployment and usage in couple clicks with Docker containers.
Currently supports Russian. 
Models for other languages recognition may be easily added in case of need.

## Deployment and launching

### Prerequsites

Installed:
* Git 
* Git LFS 
* Docker

###1. Pull repo

`$ git clone https://github.com/mvshyvk/KaldiService.git`

###2. Build Docker container

`$ cd KaldiService`<BR>
`$ docker build -t kaldi_service:1.0 ./`

###3. Launch Docker container

`$ docker run -it --rm -p 8080:8080 kaldi_service:1.0`

## Структура проекта

Файлы проекта расположены в директории /speech_recognition:

* **recognition_task.py** - script for recognition of a single audio file;
* **/tools** - набор инструментов для распознавания:
    * **data_preparator.py** - скрипт подготовки данных для распознавания;
    * **recognizer.py** - скрипт распознавания речи;
    * **segmenter.py** - скрипт сегментации речи;
    * **transcriptins_parser.py** - скрипт парсинга результатов распознавания;
* **/model** - набор файлов для модели распознавания;
* **/examples** - audio files with examples for testing purpose.

## Модель

В качестве акустической и языковой модели используется русскоязычная модель от alphacep:

http://alphacephei.com/kaldi/kaldi-ru-0.6.tar.gz

При необходимости использования собственной модели, необходимо заменить соответствующие файлы в директории /model.

> **Внимание!** Размер файла HCLG.fst составляет более 500МБ, поэтому для корректного клонирования репозитория необходимо установить на свой компьютер GitHub LFS. Также можно скачать данный файл вручную с соответствующей страницы проекта.

## Usage
### Install Postman

### Import "./service/Tests/SpeachRecognizer Tests.postman_collection.json" to postman

### Use REST interface for speech recognition

* Use "Add new task" endpoint for submitting audio files
<p align="center">
<img src="service/Tests/images/PostAudioFile.png">
</p>

Receive task id
<p align="center">
<img src="service/Tests/images/TaskIdResponse.png">
</p>

* Use "Get service status" for retrieving service status information
<p align="center">
<img src="service/Tests/images/ServiceStatus.png">
</p>

* Use "Get task status" for getting speech recognition results
<p align="center">
<img src="service/Tests/images/RecognitionResults.png ">
</p>

## OpenAPI specification

https://app.swaggerhub.com/apis-docs/mvshyvk/Kaldi_Speech_Recognition/0.9.0
