from django.shortcuts import render
from rest_framework import viewsets
from rest_framework.response import Response
from Event.models import Event 
import eventcrawler from Event
import json 


class EventViewSet(viewsets.ModelViewSet):

    def list(self, request):
        
        crawler = EventCrawler()
        events = crawler.crawl()
        data ={}
        for i, event in enumerate(events):
            data[i]= {
                'event_text':event[0],
                'event_date':event[1]
    }


        return Response(json.dumps(data))
        
    





