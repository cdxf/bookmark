# Bookmark Backend

A minimal Bookmark engine written using Spring Boot

## Setup
- application.configuration -> setup database
- gradle bootRun

## Endpoint
```
GET / -> get all categories and posts
GET /sampleDatabase -> reinitialize dataset with sample
POST /populate( categories and lists ) -> reinitialize dataset with categories and lists
```