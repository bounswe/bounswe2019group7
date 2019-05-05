from django.urls import path, include
from rest_framework.routers import DefaultRouter
from Currency import views

router = DefaultRouter()
router.register(r'', views.CurrencyViewSet)

# The API URLs are now determined automatically by the router.
urlpatterns = [
    path('convert/<str:from_currency>/<str:to_currency>/<str:amount>', views.CurrencyViewSet.as_view({"get": "convert_currency"})),
    path('', include(router.urls)),
]
