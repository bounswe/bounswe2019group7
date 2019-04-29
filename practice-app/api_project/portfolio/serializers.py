from rest_framework import serializers
from .models import Portfolio


class PortfolioSerializer(serializers.ModelSerializer):
    currencies = serializers.RelatedField(source='Currency', read_only=True)
    class Meta:
        model = Portfolio
        fields = ("id", "username", "currencies")
