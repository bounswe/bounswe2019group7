from rest_framework import serializers
from .models import Currency


class CurrencySerializer(serializers.ModelSerializer):
    class Meta:
        model = Currency
        fields = ("code", "value", "id")

