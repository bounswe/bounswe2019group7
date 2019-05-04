from django.shortcuts import get_object_or_404
from rest_framework import viewsets
from rest_framework.response import Response
from .models import Currency
from .serializers import CurrencySerializer
import requests
import json
from django.http import JsonResponse


class CurrencyViewSet(viewsets.ModelViewSet):
    """
    Provides a get method handler.
    """
    queryset = Currency.objects.all()
    serializer_class = CurrencySerializer

    def list(self, request):  # GET currencies/
        currency_list = [("USD", "TRY"), ("BTC", "USD"), ("EUR", "TRY"), ("EUR", "USD"), ("USD", "CNY")]
        data_list = []
        for currency in currency_list:
            url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency={}&to_currency={" \
                  "}&apikey=PEVAKXVH1TMR8SU9".format(
                    currency[0], currency[1])
            data_temp = requests.get(url)
            data = json.loads(data_temp.text)
            print(data)
            Currency.objects.create(from_currency=data["Realtime Currency Exchange Rate"]['1. From_Currency Code'],
                                    to_currency=data["Realtime Currency Exchange Rate"]["3. To_Currency Code"], exchange_rate=data["Realtime Currency Exchange Rate"]["5. Exchange Rate"])
            data_list.append(data)

        return Response(data_list)

    def retrieve(self, request, pk=None):  # GET articles/<id>/
        queryset = Currency.objects.all()
        currency = get_object_or_404(queryset, pk=pk)
        serializer = CurrencySerializer(currency)
        return Response(serializer.data)

    def convertCurrency(self, money, Currency):
        rate = Currency.exchange_rate
        money *= rate
        pass


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
