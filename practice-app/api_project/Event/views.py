from rest_framework import viewsets
from rest_framework.response import Response
from Event.eventcrawler import Eventcrawler
import json 


class EventViewSet(viewsets.ModelViewSet):

    def list(self, request):   
        crawler = Eventcrawler()
        events = crawler.crawl()
        data = []
        for event in events:
            data.append({'event_text':event[0],'event_date':event[1]})
        return Response(json.dumps(data))
