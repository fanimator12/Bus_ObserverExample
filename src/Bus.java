import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Bus implements UnnamedPropertyChangeSubject
{
  private String info;
  private Set<BusPassenger> passengers;
  private PropertyChangeSupport property;

  public Bus()
  {
    property = new PropertyChangeSupport(this);
    passengers = new HashSet<BusPassenger>();
    this.info = "eXpress " + hashCode() % 100;
  }

  public void passengerGettingIn(BusPassenger p)
  {
    Iterator<BusPassenger> it = passengers.iterator();
    while (it.hasNext())
      it.next().showMessage("Enter:" + p.getName());
    passengers.add(p);
  }

  public void passengerGettingOut(BusPassenger p)
  {
    passengers.remove(p);
    Iterator<BusPassenger> it = passengers.iterator();
    while (it.hasNext())
      it.next().showMessage("Leave:" + p.getName());
  }

  public String toString()
  {
    return info;
  }

  public boolean equals(Object obj)
  {
    if (!(obj instanceof Bus))
      return false;
    Bus b = (Bus) obj;
    return info.equals(b.info);
  }

  @Override public synchronized void addListener(PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(listener);

    for (BusPassenger passenger : passengers)
    {
      passenger.add("passenger", listener);
    }
  }

  @Override public synchronized void removeListener(PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(listener);

    for (BusPassenger passenger : passengers)
    {
      passenger.remove("passenger", listener);
    }
  }
}

