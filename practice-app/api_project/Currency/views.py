from django.shortcuts import get_object_or_404
from rest_framework import viewsets
from rest_framework.response import Response
from .models import Currency
from .serializers import CurrencySerializer
import requests
from django.http import JsonResponse

class CurrencyViewSet(viewsets.ModelViewSet):
    """
    Provides a get method handler.
    """
    queryset = Currency.objects.all()
    serializer_class = CurrencySerializer

    def list(self, request):  # GET articles/
        url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=BTC&to_currency=CNY&apikey=PEVAKXVH1TMR8SU9"
        data = requests.get(url)
        # serializer = CurrencySerializer(data)
        print(data.content)
        return Response(data)

    def retrieve(self, request, pk=None):  # GET articles/<id>/
        queryset = Currency.objects.all()
        currency = get_object_or_404(queryset, pk=pk)
        serializer = CurrencySerializer(currency)
        return Response(serializer.data)
'''
    def update(self, request, pk=None):  # PUT articles/<id>/  with Article object in body
        queryset = Currency.objects.all()
        article = get_object_or_404(queryset, pk=pk)
        try:
            article = Article(owner=request.data["owner"], article_title=request.data["article_title"],
                              article_text=request.data["article_text"])
            article.save()
            return Response(status=200)
        except:
            return Response(status=400)
'''
