from django.urls import path
from .views import ListPortfoliosView


urlpatterns = [
    path('songs/', ListPortfoliosView.as_view(), name="portfolios-all")
]
