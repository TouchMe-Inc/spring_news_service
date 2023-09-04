# About spring_news_service
This repository contains the news microservice.

## Search [NEW!]
`GET /api/v1/news/search`

Request Body:
```json
{
    "criteriaList": [
        {
            "key": "text",
            "operation": "CONTAINS",
            "value": "do"
        },
        {
            "key": "text",
            "operation": "CONTAINS",
            "value": "sit"
        }
    ]
}
```

Response Body:
```json
{
  "content": [
    {
      "id": 1,
      "title": "News_1",
      "text": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
      "time": "2023-08-26T08:48:18.739+00:00"
    },
    {
      "id": 15,
      "title": "News_15",
      "text": "Mi sit amet mauris commodo quis",
      "time": "2023-08-26T08:48:18.739+00:00"
    }
  ],
  "metadata": {
    "number": 0,
    "size": 5,
    "totalElements": 2,
    "totalPages": 1
  }
}
```

## Cache
There are three types of caching implemented in the service:
- lfu
- lru
- redis

Configuration example for lru cache:
```yaml
cache:
  type: lru
  capacity: 10
```

Configuration example for lfu cache:
```yaml
cache:
  type: lfu
  capacity: 10
```

Configuration example for redis cache:
```yaml
cache:
  type: redis

spring:
  data:
    redis:
      host: localhost
      port: 6379
```

## Docs
To generate documentation, you need to run the following commands:
- `./gradlew javadoc` - To generate JavaDoc documentation.
- `./gradlew asciidoctor` - To generate AsciiDoc documentation.
