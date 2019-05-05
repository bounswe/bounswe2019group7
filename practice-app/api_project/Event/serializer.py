
from rest_framework import serializers
from Event.models import Event

class EventSerializer(serializers.ModelSerializer):
	class Meta:
		model=Event
		fields=("event_text", "event_date")