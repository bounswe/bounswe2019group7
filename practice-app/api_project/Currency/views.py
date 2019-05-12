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
