from django.shortcuts import render
from rest_framework import generics
from .models import Portfolio
from .serializers import PortfolioSerializer


class ListPortfoliosView(generics.ListAPIView):
    """
    Provides a get method handler.
    """
    queryset = Portfolio.objects.all()
    serializer_class = PortfolioSerializer
