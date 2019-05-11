from django.shortcuts import get_object_or_404
from rest_framework import viewsets
from rest_framework.response import Response
from .models import Currency
from .serializers import CurrencySerializer
import requests
import json
from rest_framework.decorators import action
from django.http import HttpResponseNotFound


class CurrencyViewSet(viewsets.ModelViewSet):
    """
    Provides a get method handler.
    """
    queryset = Currency.objects.all()
    serializer_class = CurrencySerializer

    def list(self, request):  # GET currencies/
        currency_list = [("USD", "TRY"), ("TRY", "USD")]
        data_list = []
        for index, currency in enumerate(currency_list):
            url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency={}&to_currency={}&apikey=PEVAKXVH1TMR8SU9".format(
                    currency[0], currency[1])

            data_temp = requests.get(url)
            data = json.loads(data_temp.text)
            row = Currency.objects.filter(id=index)

            if len(row) == 1:
                row.update(exchange_rate=data["Realtime Currency Exchange Rate"]["5. Exchange Rate"])
            else:
                Currency.objects.create(from_currency=data["Realtime Currency Exchange Rate"]['1. From_Currency Code'],
                                        to_currency=data["Realtime Currency Exchange Rate"]["3. To_Currency Code"],
                                        exchange_rate=data["Realtime Currency Exchange Rate"]["5. Exchange Rate"],
                                        id=index)

            data_list.append(data)

        return Response(data_list)

    def retrieve(self, request, pk=None):  # GET articles/<id>/
        queryset = Currency.objects.all()
        currency = get_object_or_404(queryset, pk=pk)
        serializer = CurrencySerializer(currency)
        return Response(serializer.data)

    @action(detail=True, methods=['get'])
    def convert_currency(self, *args, **kwargs):    # GET currencies/convert/<from>/<to>/<amount>
        try:
            from_currency = str(kwargs['from_currency'])
            to_currency = str(kwargs['to_currency'])
            amount = float(kwargs['amount'])
            row = Currency.objects.filter(from_currency=from_currency)
            row = row.get(to_currency=to_currency)
            return Response({"result":row.exchange_rate*amount})
        except:
            return HttpResponseNotFound("Invalid Currency")
